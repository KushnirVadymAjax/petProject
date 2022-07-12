package com.example.petproject.controllers.entityControllers

import com.example.petproject.answers.ConsumablesAnswer
import com.example.petproject.answers.PersonAnswer
import com.example.petproject.answers.PositionAnswer
import com.example.petproject.answers.TaskAnswer
import com.example.petproject.model.*
import com.example.petproject.repository.*
import com.example.petproject.requests.ConsumablesRequest
import com.example.petproject.requests.PersonRequest
import com.example.petproject.requests.PositionRequest
import com.example.petproject.requests.TaskRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class TaskController {
    @Autowired
    private val taskRepository: TaskRepository? = null

    @Autowired
    private val positionRepository: PositionRepository? = null

    @Autowired
    private val pointRepository: PointRepository? = null

    @Autowired
    private val consumablesRepository: ConsumablesRepository? = null

    @Autowired
    private val personRepository: PersonRepository? = null

    @GetMapping("/tasks")
    fun getAllTasks(): List<TaskAnswer> {
        val temp = mutableListOf<TaskAnswer>()
        taskRepository!!.findAll()
            .forEach { e -> temp.add(conwertTaskToTaskAnswer(e)) }
        return temp
    }

    @GetMapping("/task/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): ResponseEntity<TaskAnswer> {

        val task = taskRepository!!.findById(taskId).orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body(conwertTaskToTaskAnswer(task))
    }

    @PostMapping("/task")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): ResponseEntity<TaskAnswer> {

        LOGGER.info("Request body:$requestBody")

        val positions = conwertPositionRequestToPosition(requestBody.positions)
        val consumables = conwertConsumablesRequestToConsumables(requestBody.consumables)
        val persons = conwertPersonRequestToPerson(requestBody.persons)

        var point = Point()
        val tempPoint = pointRepository?.findById(requestBody.pointID)
        if (tempPoint != null) {
            if (tempPoint.isPresent) {
                point = tempPoint.get()

            }
        }
        val task = Task(
            date = requestBody.date,
            persons = persons,
            consumables = consumables,
            point = point,
            positions = positions
        )

        return try {
            taskRepository!!.save(task)
            return ResponseEntity.ok().body(conwertTaskToTaskAnswer(task))
        } catch (e: Exception) {
            LOGGER.error(Arrays.toString(e.stackTrace))
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/task/{id}")
    fun updateTask(@PathVariable(value = "id") taskId: String, @Validated @RequestBody requestBody: TaskRequest): ResponseEntity<TaskAnswer> {

        LOGGER.info("Request body:$requestBody")

        val positions = conwertPositionRequestToPosition(requestBody.positions)
        val consumables = conwertConsumablesRequestToConsumables(requestBody.consumables)
        val persons = conwertPersonRequestToPerson(requestBody.persons)

        var point = Point()
        val tempPoint = pointRepository?.findById(requestBody.pointID)
        if (tempPoint != null) {
            if (tempPoint.isPresent) {
                point = tempPoint.get()

            }
        }
        val task = taskRepository!!.findById(taskId).orElseThrow { NotFoundException() }
        task.date = requestBody.date
        task.persons = persons
        task.consumables = consumables
        task.point = point
        task.positions = positions
        val updatedTask = taskRepository.save(task)

        return ResponseEntity.ok().body(conwertTaskToTaskAnswer(updatedTask))
    }

    @DeleteMapping("/task/{id}")
    fun deleteTask(@PathVariable(value = "id") taskId: String): Map<String, Boolean> {

        val task = taskRepository!!.findById(taskId).orElseThrow { NotFoundException() }
        taskRepository.delete(task)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TaskController::class.java)
    }

    fun conwertPositionRequestToPosition(positions: List<PositionRequest>): List<Position> {
        var temp = mutableListOf<Position>()
        for (pos in positions) {
            val tempPosition = Position(description = pos.description, comment = pos.comment)
            positionRepository?.save(tempPosition)
            temp.add(tempPosition)
        }
        return temp
    }

    fun conwertConsumablesRequestToConsumables(consumables: List<ConsumablesRequest>): List<Consumables> {
        var temp = mutableListOf<Consumables>()
        for (con in consumables) {
            val tempConsumables = Consumables(name = con.name, description = con.description, comment = con.comment)
            consumablesRepository?.save(tempConsumables)
            temp.add(tempConsumables)
        }
        return temp
    }

    fun conwertPersonRequestToPerson(persons: List<PersonRequest>): List<Person> {
        var temp = mutableListOf<Person>()
        for (per in persons) {
            val tempPerson =
                Person(name = per.name, fullName = per.fullName, phoneNumber = per.phoneNumber, admin = per.admin)
            personRepository?.save(tempPerson)
            temp.add(tempPerson)
        }
        return temp
    }

    fun conwertTaskToTaskAnswer(task: Task): TaskAnswer {
        return TaskAnswer(
            task.id.toString(), task.date, task.point.id.toString(),
            conwertPositionsToPositionAnswer(task.positions),
            conwertConsumablesToConsumablesAnswer(task.consumables),
            conwertPersonsToPersonAnswer(task.persons)
        )

    }

    fun conwertPositionsToPositionAnswer(positions: List<Position>): List<PositionAnswer> {
        var temp = mutableListOf<PositionAnswer>()
        for (pos in positions) {
            val tempPosition =
                PositionAnswer(id = pos.id.toString(), description = pos.description, comment = pos.comment)
            temp.add(tempPosition)
        }
        return temp
    }

    fun conwertConsumablesToConsumablesAnswer(consumables: List<Consumables>): List<ConsumablesAnswer> {
        var temp = mutableListOf<ConsumablesAnswer>()
        for (con in consumables) {
            val tempConsumables = ConsumablesAnswer(
                id = con.id.toString(),
                name = con.name,
                description = con.description,
                comment = con.comment
            )
            temp.add(tempConsumables)
        }
        return temp
    }

    fun conwertPersonsToPersonAnswer(persons: List<Person>): List<PersonAnswer> {
        var temp = mutableListOf<PersonAnswer>()
        for (per in persons) {
            val tempPerson =
                PersonAnswer(
                    id = per.id.toString(),
                    name = per.name,
                    fullName = per.fullName,
                    phoneNumber = per.phoneNumber,
                    admin = per.admin
                )
            temp.add(tempPerson)
        }
        return temp
    }

}