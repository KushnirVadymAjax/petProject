package com.example.petproject.controllers.entityControllers

import com.example.petproject.model.*
import com.example.petproject.repository.*
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
    fun getAllTasks(): List<Task> {
        return taskRepository!!.findAll()
    }

    @GetMapping("/task/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): ResponseEntity<Task> {

        val task = taskRepository!!.findById(taskId).orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body(task)
    }

    @PostMapping("/task")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): ResponseEntity<Task> {

        LOGGER.info("Request body:$requestBody")

        val positions = mutableListOf<Position>()
        val consumables = mutableListOf<Consumables>()
        val persons = mutableListOf<Person>()

        for (pos in requestBody.positions) {
            val tempPosition = Position(description = pos.description, comment = pos.comment)
            positionRepository?.save(tempPosition)
            positions.add(tempPosition)
        }

        for (con in requestBody.consumables) {
            val tempConsumables = Consumables(name = con.name, description = con.description, comment = con.comment)
            consumablesRepository?.save(tempConsumables)
            consumables.add(tempConsumables)
        }

        for (per in requestBody.persons) {
            val tempPerson =
                Person(name = per.name, fullName = per.fullName, phoneNumber = per.phoneNumber, admin = per.admin)
            personRepository?.save(tempPerson)
            persons.add(tempPerson)
        }
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
            ResponseEntity(null, HttpStatus.OK)
        } catch (e: Exception) {
            LOGGER.error(Arrays.toString(e.stackTrace))
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/task/{id}")
    fun updateTask(
        @PathVariable(value = "id") taskId: String, @Validated @RequestBody requestBody: TaskRequest
    ): ResponseEntity<Task> {

        LOGGER.info("Request body:$requestBody")

        val positions = mutableListOf<Position>()
        val consumables = mutableListOf<Consumables>()
        val persons = mutableListOf<Person>()

        for (pos in requestBody.positions) {
            val tempPosition = Position(description = pos.description, comment = pos.comment)
            positionRepository?.save(tempPosition)
            positions.add(tempPosition)
        }

        for (con in requestBody.consumables) {
            val tempConsumables = Consumables(name = con.name, description = con.description, comment = con.comment)
            consumablesRepository?.save(tempConsumables)
            consumables.add(tempConsumables)
        }

        for (per in requestBody.persons) {
            val tempPerson =
                Person(name = per.name, fullName = per.fullName, phoneNumber = per.phoneNumber, admin = per.admin)
            personRepository?.save(tempPerson)
            persons.add(tempPerson)
        }
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
        return ResponseEntity.ok(updatedTask)
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
}