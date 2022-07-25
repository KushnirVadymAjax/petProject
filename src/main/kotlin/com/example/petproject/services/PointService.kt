package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import org.bson.types.ObjectId
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PointService {
    fun getAllPoints(): Flux<PointAnswer>

    fun getPointById(pointId: ObjectId): Mono<PointAnswer>

    fun addPoint(requestBody: PointRequest): Mono<PointAnswer>

    fun updatePoint(pointId: ObjectId, requestBody: PointRequest): Mono<PointAnswer>

    fun deletePoint(pointId: ObjectId): Mono<Void>
}