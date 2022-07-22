package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.services.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class TaskController(val taskService: TaskService) {
    @GetMapping("/tasks")
    fun getAllTasks(): ResponseEntity<List<TaskAnswer>> {
        return taskService.getAllTasks()
    }

    @GetMapping("/tasks/{id}")
    fun getTaskById(@PathVariable(value = "id") taskId: String): ResponseEntity<TaskAnswer> {
        return taskService.getTaskById(taskId)
    }

    @GetMapping("/tasks/getTaskAfterDate/{date}")
    fun getTaskAfterDate(@PathVariable(value = "date") date: String): ResponseEntity<List<TaskAnswer>> {
        return taskService.getTaskAfterDate(date)
    }

    @PostMapping("/tasks")
    fun addTask(@Validated @RequestBody requestBody: TaskRequest): ResponseEntity<TaskAnswer> {
        return taskService.addTask(requestBody)
    }

    @PutMapping("/tasks/{id}")
    fun updateTask(
        @PathVariable(value = "id") taskId: String,
        @Validated @RequestBody requestBody: TaskRequest
    ): ResponseEntity<TaskAnswer> {
        return taskService.updateTask(taskId, requestBody)
    }

    @DeleteMapping("/tasks/{id}")
    fun deleteTask(@PathVariable(value = "id") taskId: String): ResponseEntity<Unit> {
        return taskService.deleteTask(taskId)
    }
}