package com.example.petproject.repository

import com.example.petproject.model.Point
import com.example.petproject.model.Task
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface PointRepository : MongoRepository<Point, ObjectId> {
    fun findById(id: String?): Optional<Point>

    fun findPointByTaskListContaining(task: Task): Point
}