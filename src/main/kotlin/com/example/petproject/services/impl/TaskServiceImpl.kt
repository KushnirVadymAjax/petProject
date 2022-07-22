package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.model.Point
import com.example.petproject.model.Task
import com.example.petproject.repository.PointRepository
import com.example.petproject.repository.PositionRepository
import com.example.petproject.repository.TaskRepository
import com.example.petproject.services.TaskService
import com.example.petproject.utils.TaskUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class TaskServiceImpl(
    val taskRepository: TaskRepository,
    val positionRepository: PositionRepository,
    val pointRepository: PointRepository
) : TaskService {

    override fun getAllTasks(): ResponseEntity<List<TaskAnswer>> {
        val temp = mutableListOf<TaskAnswer>()
        taskRepository.findAll().forEach { e -> temp.add(TaskUtils.convertTaskToTaskAnswer(e)) }
        return ResponseEntity.ok(temp)
    }

    override fun getTaskById(taskId: String): ResponseEntity<TaskAnswer> {
        val task = taskRepository.findById(taskId).orElseThrow { ChangeSetPersister.NotFoundException() }
        return ResponseEntity.ok().body(TaskUtils.convertTaskToTaskAnswer(task))
    }

    override fun getTaskAfterDate(date: String): ResponseEntity<List<TaskAnswer>> {
        val temp = mutableListOf<TaskAnswer>()
        taskRepository.findTasksAfterDate(LocalDate.parse(date))
            .forEach { e -> temp.add(TaskUtils.convertTaskToTaskAnswer(e)) }
        return ResponseEntity.ok(temp)
    }

    override fun addTask(requestBody: TaskRequest): ResponseEntity<TaskAnswer> {
        val tempTask = Task(
            date = requestBody.date
        )

        val positions = TaskUtils.convertPositionRequestToPosition(requestBody.positions, tempTask)

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
            return ResponseEntity(TaskUtils.convertTaskToTaskAnswer(tempTask), HttpStatus.CREATED)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    override fun updateTask(taskId: String, requestBody: TaskRequest): ResponseEntity<TaskAnswer> {

        val task = taskRepository.findById(taskId).orElseThrow { ChangeSetPersister.NotFoundException() }

        val positions = TaskUtils.convertPositionRequestToPosition(requestBody.positions, task)


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
            return ResponseEntity(TaskUtils.convertTaskToTaskAnswer(task), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }
    }

    override fun deleteTask(taskId: String): ResponseEntity<Unit> {

        val task = taskRepository.findById(taskId).orElseThrow { ChangeSetPersister.NotFoundException() }
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
}