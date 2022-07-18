package com.example.petproject.repository

import com.example.petproject.model.Task
import com.example.petproject.mongoTemplate.TaskCustomRepository
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface TaskRepository : MongoRepository<Task, ObjectId>,TaskCustomRepository {
    fun findById(id: String?): Optional<Task>
}