package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.model.*
import com.example.petproject.repository.*
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.jsonMapping.requests.TaskRequest
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class TaskController(
    private val taskRepository: TaskRepository,
    private val positionRepository: PositionRepository,
    private val pointRepository: PointRepository
) {
    @GetMapping("/tasks")
    fun getAllTasks(): ResponseEntity<List<TaskAnswer>> {
        val temp = mutableListOf<TaskAnswer>()
        taskRepository.findAll().forEach { e -> temp.add(conwertTaskToTaskAnswer(e)) }
        return ResponseEntity.ok(temp)
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): ResponseEntity<TaskAnswer> {

        val task = taskRepository.findById(taskId).orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body(conwertTaskToTaskAnswer(task))
    }

    @PostMapping("/tasks")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): ResponseEntity<TaskAnswer> {

        val tempTask = Task(
            date = requestBody.date
        )

        val positions = conwertPositionRequestToPosition(requestBody.positions, tempTask)

        var point: Point? = null
        val tempPoint = pointRepository.findById(requestBody.pointID)
        if (tempPoint.isPresent) {
            point = tempPoint.get()
            point.taskList?.add(tempTask)
            pointRepository.save(point)
        }
        tempTask.point = point
        tempTask.positions = positions

        return try {
            taskRepository.save(tempTask)
            return ResponseEntity(conwertTaskToTaskAnswer(tempTask), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(
        @PathVariable(value = "id") taskId: String, @Validated @RequestBody requestBody: TaskRequest
    ): ResponseEntity<TaskAnswer> {

        val task = taskRepository.findById(taskId).orElseThrow { NotFoundException() }

        val positions = conwertPositionRequestToPosition(requestBody.positions, task)


        var point: Point? = null
        val tempPoint = pointRepository.findById(requestBody.pointID)
        if (tempPoint.isPresent) {
            point = tempPoint.get()
            point.taskList?.add(task)
            pointRepository.save(point)
        }

        task.date = requestBody.date
        task.point = point
        task.positions = positions

        return try {
            taskRepository.save(task)
            return ResponseEntity(conwertTaskToTaskAnswer(task), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }

    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable(value = "id") taskId: String): ResponseEntity<Unit> {

        val task = taskRepository.findById(taskId).orElseThrow { NotFoundException() }
        val posList = positionRepository.findByTaskId(task.id)

        for (pos in posList) {
            positionRepository.delete(pos)
        }


        if (task.point != null) {
            val point = pointRepository.findPointByTaskListContaining(task)
            pointRepository.delete(point)
        }
        taskRepository.delete(task)

        return ResponseEntity.noContent().build()

    }

    fun conwertPositionRequestToPosition(positions: List<PositionRequest>, task: Task): MutableList<Position> {
        val temp = mutableListOf<Position>()
        for (pos in positions) {
            val tempPosition = Position(description = pos.description, comment = pos.comment, task = task)
            positionRepository.save(tempPosition)
            temp.add(tempPosition)
        }
        return temp
    }


    fun conwertTaskToTaskAnswer(task: Task): TaskAnswer {
        return TaskAnswer(
            task.id.toString(),
            task.date,
            task.point?.id.toString(),
            conwertPositionsToPositionAnswer(task.positions)
        )

    }

    fun conwertPositionsToPositionAnswer(positions: List<Position>): MutableList<PositionAnswer> {
        val temp = mutableListOf<PositionAnswer>()
        for (pos in positions) {
            val tempPosition =
                PositionAnswer(id = pos.id.toString(), description = pos.description, comment = pos.comment)
            temp.add(tempPosition)
        }
        return temp
    }

}