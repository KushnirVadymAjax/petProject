package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.jsonMapping.requests.PointRequest
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class PointController(private val pointRepository: PointRepository) {
    @GetMapping("/points")
    fun getAllPoints(): ResponseEntity<List<PointAnswer>> {
        val temp = mutableListOf<PointAnswer>()
        pointRepository.findAll().forEach { e ->
            temp.add(
                PointAnswer(
                    e.id.toString(), e.name, e.adress, e.latitude, e.longitude, e.contactPerson
                )
            )
        }
        return ResponseEntity.ok(temp)
    }

    @GetMapping("/points/{id}")
    fun getPointById(@PathVariable(value = "id") pointId: String): ResponseEntity<PointAnswer> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body(
            PointAnswer(
                point.id.toString(), point.name, point.adress, point.latitude, point.longitude, point.contactPerson
            )
        )
    }

    @PostMapping("/points")
    fun addPoint(@Validated @RequestBody requestBody: PointRequest): ResponseEntity<PointAnswer> {
        val point = Point(
            name = requestBody.name,
            adress = requestBody.adress,
            comment = requestBody.comment,
            contactNumber = requestBody.contactNumber,
            contactPerson = requestBody.contactPerson,
            latitude = requestBody.latitude,
            longitude = requestBody.longitude
        )
        return try {
            pointRepository.save(point)
            return ResponseEntity(
                PointAnswer(
                    point.id.toString(), point.name, point.adress, point.latitude, point.longitude, point.contactPerson
                ), HttpStatus.CREATED
            )
        } catch (e: Exception) {
            ResponseEntity<PointAnswer>(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/points/{id}")
    fun updatePoint(
        @PathVariable(value = "id") pointId: String, @Validated @RequestBody requestBody: PointRequest
    ): ResponseEntity<PointAnswer> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { NotFoundException() }

        point.adress = requestBody.adress
        point.comment = requestBody.comment
        point.contactNumber = requestBody.contactNumber
        point.name = requestBody.name
        point.longitude = requestBody.longitude
        point.latitude = requestBody.latitude
        point.contactPerson = requestBody.contactPerson
        return try {
            val updatedPoint: Point = pointRepository.save(point)

            return ResponseEntity(
                PointAnswer(
                    updatedPoint.id.toString(),
                    updatedPoint.name,
                    updatedPoint.adress,
                    updatedPoint.latitude,
                    updatedPoint.longitude,
                    updatedPoint.contactPerson
                ), HttpStatus.OK
            )
        } catch (e: Exception) {
            ResponseEntity(null, HttpStatus.BAD_GATEWAY)
        }

    }

    @DeleteMapping("/points/{id}")
    fun deletePoint(@PathVariable(value = "id") pointId: String): ResponseEntity<Unit> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { NotFoundException() }
        pointRepository.delete(point)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return ResponseEntity.noContent().build()
    }
}