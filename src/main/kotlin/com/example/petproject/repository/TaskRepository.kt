package com.example.petproject.repository

import com.example.petproject.model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TaskRepository : MongoRepository<Task, ObjectId> {
    fun findById(id: String?): Optional<Task>
}