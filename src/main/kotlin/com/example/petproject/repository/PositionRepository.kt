package com.example.petproject.repository

import com.example.petproject.model.Position
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PositionRepository : ReactiveMongoRepository<Position, ObjectId> {

}