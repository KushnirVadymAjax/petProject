package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.services.PointService
import org.bson.types.ObjectId
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class PointController(val pointService: PointService) {
    @GetMapping("/points")
    fun getAllPoints(): Flux<PointAnswer> {
        return pointService.getAllPoints()
    }

    @GetMapping("/points/{id}")
    fun getPointById(@PathVariable(value = "id") pointId: String): Mono<PointAnswer> {
        return pointService.getPointById(ObjectId(pointId))
    }

    @PostMapping("/points")
    fun addPoint(@Validated @RequestBody requestBody: PointRequest): Mono<PointAnswer> {
        return pointService.addPoint(requestBody)
    }

    @PutMapping("/points/{id}")
    fun updatePoint(
        @PathVariable(value = "id") pointId: String, @Validated @RequestBody requestBody: PointRequest
    ): Mono<PointAnswer> {
        return pointService.updatePoint(ObjectId(pointId), requestBody)
    }

    @DeleteMapping("/points/{id}")
    fun deletePoint(@PathVariable(value = "id") pointId: String): Mono<Void> {
        return pointService.deletePoint(ObjectId(pointId))
    }
}