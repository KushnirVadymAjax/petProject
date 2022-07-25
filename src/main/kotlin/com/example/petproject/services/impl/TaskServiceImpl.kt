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
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@Service
class TaskServiceImpl(
    val taskRepository: TaskRepository,
    val positionRepository: PositionRepository,
    val pointRepository: PointRepository
) : TaskService {

    override fun getAllTasks(): Flux<TaskAnswer> {
        return taskRepository.findAll()
            .map { e -> TaskUtils.convertTaskToTaskAnswer(e) }.delayElements(Duration.ofSeconds(1))
    }

    override fun getTaskById(taskId: ObjectId): Mono<TaskAnswer> =
        taskRepository.findById(taskId).map { e -> TaskUtils.convertTaskToTaskAnswer(e) }.switchIfEmpty {
            Mono.error(
                NotFoundException()
            )
        }


//    override fun getTaskAfterDate(date: String): Flux<TaskAnswer> {
//        val temp = mutableListOf<TaskAnswer>()
//        taskRepository.findTasksAfterDate(LocalDate.parse(date))
//            .forEach { e -> temp.add(TaskUtils.convertTaskToTaskAnswer(e)) }
//        return ResponseEntity.ok(temp)
//    }

    override fun addTask(requestBody: TaskRequest): Mono<TaskAnswer> {
        val tempTask = Task(
            date = requestBody.date
        )

        val positions = TaskUtils.convertPositionRequestToPosition(requestBody.positions)

        var point: Point? = null
        val tempPoint = pointRepository.findById(requestBody.pointID)
        if (tempPoint.isPresent) {
            point = tempPoint.get()
        }
        tempTask.point = point
        tempTask.positions = positions

        return taskRepository.save(tempTask).map { e -> TaskUtils.convertTaskToTaskAnswer(e) }
    }


    override fun updateTask(taskId: ObjectId, requestBody: TaskRequest): Mono<TaskAnswer> {

        val tempTask = taskRepository.findById(taskId)

        val positions = TaskUtils.convertPositionRequestToPosition(requestBody.positions)

        var point: Point? = null
        val tempPoint = pointRepository.findById(requestBody.pointID)
        if (tempPoint.isPresent) {
            point = tempPoint.get()
        }

        return tempTask.flatMap {
            taskRepository.save(it.apply {
                it.date = requestBody.date
                it.point = point
                it.positions = positions
            }).map { e -> TaskUtils.convertTaskToTaskAnswer(e) }
        }
    }

    override fun deleteTask(taskId: ObjectId): Mono<Void> {
        return taskRepository.findById(taskId)
            .switchIfEmpty {
                Mono.error(
                    NotFoundException()
                )
            }
            .flatMap(taskRepository::delete)

    }
}