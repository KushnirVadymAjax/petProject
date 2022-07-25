package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.TaskRequest
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface TaskService {
    fun getAllTasks(): Flux<TaskAnswer>

    fun getTaskById(taskId: ObjectId): Mono<TaskAnswer>

//    fun getTaskAfterDate(date: String): Flux<TaskAnswer>

    fun addTask(requestBody: TaskRequest): Mono<TaskAnswer>

    fun updateTask(taskId: ObjectId, requestBody: TaskRequest): Mono<TaskAnswer>

    fun deleteTask(taskId: ObjectId): Mono<Void>
}