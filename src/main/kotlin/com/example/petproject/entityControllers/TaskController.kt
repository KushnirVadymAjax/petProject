package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.model.*
import com.example.petproject.repository.*
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.jsonMapping.requests.TaskRequest
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

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
        taskRepository.findAll().forEach { e -> temp.add(convertTaskToTaskAnswer(e)) }
        return ResponseEntity.ok(temp)
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): ResponseEntity<TaskAnswer> {

        val task = taskRepository.findById(taskId).orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body(convertTaskToTaskAnswer(task))
    }

    @GetMapping("/tasks/getTaskAfterDate/{date}")
    fun getTaskAfterDate(@PathVariable(value = "date")  date: String): ResponseEntity<List<TaskAnswer>> {
        val temp = mutableListOf<TaskAnswer>()
        taskRepository.findTasksAfterDate(LocalDate.parse(date)).forEach { e -> temp.add(convertTaskToTaskAnswer(e)) }
        return ResponseEntity.ok(temp)
    }

    @PostMapping("/tasks")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): ResponseEntity<TaskAnswer> {

        val tempTask = Task(
            date = requestBody.date
        )

        val positions = convertPositionRequestToPosition(requestBody.positions, tempTask)

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
            return ResponseEntity(convertTaskToTaskAnswer(tempTask), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(
        @PathVariable(value = "id") taskId: String, @Validated @RequestBody requestBody: TaskRequest
    ): ResponseEntity<TaskAnswer> {

        val task = taskRepository.findById(taskId).orElseThrow { NotFoundException() }

        val positions = convertPositionRequestToPosition(requestBody.positions, task)


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
            return ResponseEntity(convertTaskToTaskAnswer(task), HttpStatus.OK)
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

    fun convertPositionRequestToPosition(positions: List<PositionRequest>, task: Task): MutableList<Position> {
        val temp = mutableListOf<Position>()
        for (pos in positions) {
            val tempPosition = Position(description = pos.description, comment = pos.comment, task = task)
            positionRepository.save(tempPosition)
            temp.add(tempPosition)
        }
        return temp
    }

    fun convertTaskToTaskAnswer(task: Task): TaskAnswer {
        return TaskAnswer(
            task.id.toString(),
            task.date,
            task.point?.id.toString(),
            convertPositionsToPositionAnswer(task.positions)
        )
    }

    fun convertPositionsToPositionAnswer(positions: List<Position>): MutableList<PositionAnswer> {
        val temp = mutableListOf<PositionAnswer>()
        for (pos in positions) {
            val tempPosition =
                PositionAnswer(id = pos.id.toString(), description = pos.description, comment = pos.comment)
            temp.add(tempPosition)
        }
        return temp
    }
}