package com.example.petproject.repository

import com.example.petproject.model.Point
import org.springframework.data.mongodb.repository.MongoRepository

interface PointRepository : MongoRepository<Point, String> {
}