package com.example.petproject.repository

import com.example.petproject.model.Position
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PositionRepository : MongoRepository<Position, ObjectId> {
    fun findByTaskId(taskId: ObjectId): List<Position>

}