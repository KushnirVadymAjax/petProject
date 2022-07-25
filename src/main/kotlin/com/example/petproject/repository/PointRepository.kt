package com.example.petproject.repository

import com.example.petproject.model.Point
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import java.util.*

interface PointRepository : ReactiveMongoRepository<Point, ObjectId> {
    fun findById(id: String?): Optional<Point>

}