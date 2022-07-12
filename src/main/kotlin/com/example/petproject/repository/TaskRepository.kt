package com.example.petproject.repository

import com.example.petproject.model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TaskRepository : MongoRepository<Task, String> {
    fun findOneById(id: ObjectId): Task
}