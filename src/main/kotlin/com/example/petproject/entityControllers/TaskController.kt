package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.services.TaskService
import org.bson.types.ObjectId
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class TaskController(val taskService: TaskService) {
    @GetMapping("/tasks")
    fun getAllTasks(): Flux<TaskAnswer> {
        return taskService.getAllTasks()
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): Mono<TaskAnswer> {
        return taskService.getTaskById(ObjectId(taskId))
    }

    @GetMapping("/tasks/getTaskAfterDate/{date}")
    fun getTaskAfterDate(@PathVariable(value = "date") date: String): Flux<TaskAnswer> {
        return taskService.getTaskAfterDate(date)
    }

    @PostMapping("/tasks")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): Mono<TaskAnswer> {
        return taskService.addTask(requestBody)
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(
        @PathVariable(value = "id") taskId: String,
        @Validated @RequestBody requestBody: TaskRequest
    ): Mono<TaskAnswer> {
        return taskService.updateTask(ObjectId(taskId), requestBody)
    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable(value = "id") taskId: String): Mono<Void> {
        return taskService.deleteTask(ObjectId(taskId))
    }
}