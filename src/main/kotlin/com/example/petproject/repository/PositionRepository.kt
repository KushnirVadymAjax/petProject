package com.example.petproject.repository

import com.example.petproject.model.Position
import org.springframework.data.mongodb.repository.MongoRepository

interface PositionRepository : MongoRepository<Position, String> {
}