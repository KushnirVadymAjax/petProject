package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.TaskRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface TaskService {
    fun getAllTasks(): ResponseEntity<List<TaskAnswer>>

    fun getTaskById(taskId: String): ResponseEntity<TaskAnswer>

    fun getTaskAfterDate(date: String): ResponseEntity<List<TaskAnswer>>

    fun addTask(requestBody: TaskRequest): ResponseEntity<TaskAnswer>

    fun updateTask(taskId: String, requestBody: TaskRequest): ResponseEntity<TaskAnswer>

    fun deleteTask(taskId: String): ResponseEntity<Unit>
}