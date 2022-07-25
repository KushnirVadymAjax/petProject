package com.example.petproject.repository

import com.example.petproject.model.Task
import com.example.petproject.mongoTemplate.TaskCustomRepository
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface TaskRepository : ReactiveMongoRepository<Task, ObjectId>,TaskCustomRepository {
}