package com.example.petproject

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.model.Task
import com.example.petproject.repository.TaskRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.NoSuchElementException
import java.util.Optional
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.DeclaredMemberIndex.Empty

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetProjectApplicationTests @Autowired constructor(
    private val taskRepository: TaskRepository, private val restTemplate: TestRestTemplate
) {
    private val defaultTaskId = ObjectId.get()

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        taskRepository.deleteAll()
    }

    private fun getRootUrl(): String? = "http://localhost:$port/api/v1/tasks"

    private fun saveOneTask() = taskRepository.save(Task(defaultTaskId, LocalDate.now()))

    private fun prepareTaskRequest() = TaskRequest(
        LocalDate.now(),
        positions = mutableListOf(PositionRequest("des1", "com1"))
    )

    @Test
    fun `should return all tasks`() {
        saveOneTask()

        val response = restTemplate.getForEntity(
            getRootUrl(), List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun `should return single task by id`() {
        saveOneTask()

        val response = restTemplate.getForEntity(
            getRootUrl() + "/$defaultTaskId", Task::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(defaultTaskId, response.body?.id)
    }

    @Test
    fun `should create new task`() {
        val taskRequest = prepareTaskRequest()

        val response = restTemplate.postForEntity(
            getRootUrl(), taskRequest, TaskAnswer::class.java
        )


        assertEquals(201, response.statusCode.value())
        assertNotNull(response.body)
        assertNotNull(response.body?.id)
        assertEquals(taskRequest.positions.size, response.body?.positions?.size)
    }

    @Test
    fun `should update existing task`() {
        saveOneTask()
        val taskRequest = prepareTaskRequest()

        val updateResponse = restTemplate.exchange(
            getRootUrl() + "/$defaultTaskId",
            HttpMethod.PUT,
            HttpEntity(taskRequest, HttpHeaders()),
            TaskAnswer::class.java
        )
        val updatedTask = taskRepository.findById(defaultTaskId).get()

        assertEquals(200, updateResponse.statusCode.value())
        assertEquals(defaultTaskId, updatedTask.id)
        assertEquals(taskRequest.positions.size, updatedTask.positions.size)
    }

    @Test
    fun `should delete existing task`() {
        saveOneTask()

        val delete = restTemplate.exchange(
            getRootUrl() + "/$defaultTaskId",
            HttpMethod.DELETE,
            HttpEntity(null, HttpHeaders()),
            ResponseEntity::class.java
        )

        assertEquals(204, delete.statusCode.value())
        assertThrows(NoSuchElementException::class.java) { taskRepository.findById(defaultTaskId).get() }
    }
}
