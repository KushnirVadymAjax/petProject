package com.example.petproject.repository

import com.example.petproject.model.Point
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface PointRepository : ReactiveMongoRepository<Point, ObjectId> {

}