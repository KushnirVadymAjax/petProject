package com.example.petproject

import com.example.petproject.entityControllers.TaskController
import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.model.Point
import com.example.petproject.model.Position
import com.example.petproject.model.Task
import com.example.petproject.repository.PointRepository
import com.example.petproject.repository.PositionRepository
import com.example.petproject.repository.TaskRepository
import com.example.petproject.services.impl.PointServiceImpl
import com.example.petproject.services.impl.PositionServiceImpl
import com.example.petproject.services.impl.TaskServiceImpl
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.time.LocalDate


@WebFluxTest(TaskController::class)
@ExtendWith(SpringExtension::class)
@Import(TaskServiceImpl::class, PointServiceImpl::class, PositionServiceImpl::class)
class TaskTests {
    @MockBean
    lateinit var taskRepository: TaskRepository

    @MockBean
    lateinit var pointRepository: PointRepository

    @MockBean
    lateinit var positionRepository: PositionRepository

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should return all tasks`() {
        val fTask = Task(ObjectId.get(), LocalDate.now())
        val sTask = Task(ObjectId.get(), LocalDate.now())

        val taskList = listOf(fTask, sTask).toFlux()

        Mockito.`when`(taskRepository.findAll())
            .thenReturn(taskList)

        webClient.get().uri("http://localhost:8080/api/v1/tasks").header(HttpHeaders.ACCEPT, "application/json")
            .exchange().expectStatus().isOk.expectBodyList(TaskAnswer::class.java)

        Mockito.verify(taskRepository, VerificationModeFactory.times(1)).findAll()
    }

    @Test
    fun `should return single task by id`() {
        val objectId = ObjectId.get()
        val date = LocalDate.now()

        val fPos = Position(ObjectId.get(), "fPos", "comment fPos")
        val sPos = Position(ObjectId.get(), "sPos", "comment sPos")

        val posList = mutableListOf(fPos, sPos)


        val expected = Task(objectId, date, positions = posList)

        Mockito.`when`(taskRepository.findById(objectId))
            .thenReturn(Mono.just(expected))

        webClient.get().uri("http://localhost:8080/api/v1/tasks/{id}", objectId)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange().expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.date").isEqualTo(date.toString())
            .jsonPath("$.positions").isArray
            .jsonPath("$.positions[0].description").isEqualTo("fPos")


        Mockito.verify(taskRepository).findById(objectId)
    }

    @Test
    fun `should create new task`() {
        val objectId = ObjectId.get()
        val date = LocalDate.now()

        val fPos = Position(ObjectId.get(), "fPos", "comment fPos")
        val sPos = Position(ObjectId.get(), "sPos", "comment sPos")

        val posList = mutableListOf(fPos, sPos)

        val expected = Task(objectId, date, positions = posList, point = Point(objectId))
        val request = TaskRequest(
            date,
            positions = mutableListOf(PositionRequest("fPos", "comment fPos")),
            pointID = objectId.toString()
        )

        Mockito.`when`(taskRepository.save(any(Task::class.java)))
            .thenReturn(Mono.just(expected))

        Mockito.`when`(pointRepository.findById(objectId))
            .thenReturn(Mono.just(Point(objectId)))

        webClient.post().uri("http://localhost:8080/api/v1/tasks")
            .header(HttpHeaders.ACCEPT, "application/json")
            .body(Mono.just(request), TaskRequest::class.java)
            .exchange().expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.date").isEqualTo(date.toString())
            .jsonPath("$.positions").isArray
            .jsonPath("$.positions[0].description").isEqualTo("fPos")


        Mockito.verify(taskRepository).save(expected)
    }

    @Test
    fun `should update existing task`() {
        val objectId = ObjectId.get()
        val date = LocalDate.now()

        val fPos = Position(ObjectId.get(), "fPos", "comment fPos")
        val sPos = Position(ObjectId.get(), "sPos", "comment sPos")
        val changesPos = Position(ObjectId.get(), "changesPos", "comment changesPos")
        val posList = mutableListOf(fPos, sPos)
        val changePosList = mutableListOf(fPos, sPos,changesPos)

        val request = Task(objectId, date, positions = posList, point = Point(objectId))
        val taksRequest = TaskRequest(date, positions = mutableListOf(
            PositionRequest("fPos", "comment fPos"),
            PositionRequest("sPos", "comment sPos")
        ) , pointID = objectId.toString())
        val expected = Task(objectId, date, positions = changePosList, point = Point(objectId))

        Mockito.`when`(taskRepository.save(any(Task::class.java)))
            .thenReturn(Mono.just(expected))

        Mockito.`when`(taskRepository.findById(objectId))
            .thenReturn(Mono.just(request))

        Mockito.`when`(pointRepository.findById(any(ObjectId::class.java)))
            .thenReturn(Mono.just(Point(objectId)))

        webClient.put().uri("http://localhost:8080/api/v1/tasks/{id}",objectId)
            .header(HttpHeaders.ACCEPT, "application/json")
            .body(Mono.just(taksRequest), TaskRequest::class.java)
            .exchange().expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.date").isEqualTo(date.toString())
            .jsonPath("$.positions").isArray
            .jsonPath("$.positions[0].description").isEqualTo("fPos")

    }

    @Test
    fun `should delete existing task`() {
        val objectId = ObjectId.get()
        val voidReturn = Mono.empty<Void>()
        Mockito
            .`when`(taskRepository.deleteById(objectId))
            .thenReturn(voidReturn)

        webClient.delete().uri("http://localhost:8080/api/v1/tasks/{id}", objectId)
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun `should return list of task after date`() {

        val date = LocalDate.now().minusDays(1)

        val fTask = Task(ObjectId.get(), LocalDate.now())
        val sTask = Task(ObjectId.get(), LocalDate.now())

        val taskList = listOf(fTask, sTask).toFlux()

        Mockito.`when`(taskRepository.findTasksAfterDate(date))
            .thenReturn(taskList)

        webClient.get().uri("http://localhost:8080/api/v1/tasks/getTaskAfterDate/{date}", date)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange().expectStatus().isOk.expectBodyList(TaskAnswer::class.java)

        Mockito.verify(taskRepository, VerificationModeFactory.times(1)).findTasksAfterDate(date)
    }
}
