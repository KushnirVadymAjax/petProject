package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.services.PointService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class PointController(val pointService: PointService) {
    @GetMapping("/points")
    fun getAllPoints(): ResponseEntity<List<PointAnswer>> {
        return pointService.getAllPoints()
    }

    @GetMapping("/points/{id}")
    fun getPointById(@PathVariable(value = "id") pointId: String): ResponseEntity<PointAnswer> {
        return pointService.getPointById(pointId)
    }

    @PostMapping("/points")
    fun addPoint(@Validated @RequestBody requestBody: PointRequest): ResponseEntity<PointAnswer> {
        return pointService.addPoint(requestBody)
    }

    @PutMapping("/points/{id}")
    fun updatePoint(
        @PathVariable(value = "id") pointId: String,
        @Validated @RequestBody requestBody: PointRequest
    ): ResponseEntity<PointAnswer> {
        return pointService.updatePoint(pointId, requestBody)
    }

    @DeleteMapping("/points/{id}")
    fun deletePoint(@PathVariable(value = "id") pointId: String): ResponseEntity<Unit> {
        return pointService.deletePoint(pointId)
    }
}