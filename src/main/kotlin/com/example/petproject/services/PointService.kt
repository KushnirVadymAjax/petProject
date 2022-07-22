package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface PointService {
    fun getAllPoints(): ResponseEntity<List<PointAnswer>>

    fun getPointById(pointId: String): ResponseEntity<PointAnswer>

    fun addPoint(requestBody: PointRequest): ResponseEntity<PointAnswer>

    fun updatePoint(pointId: String, requestBody: PointRequest): ResponseEntity<PointAnswer>

    fun deletePoint(pointId: String): ResponseEntity<Unit>
}