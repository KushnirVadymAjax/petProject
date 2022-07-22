package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.services.PointService
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.util.HashMap

@Service
class PointServiceImpl(val pointRepository: PointRepository) : PointService {

    override fun getAllPoints(): ResponseEntity<List<PointAnswer>> {
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

    override fun getPointById(pointId: String): ResponseEntity<PointAnswer> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { ChangeSetPersister.NotFoundException() }
        return ResponseEntity.ok().body(
            PointAnswer(
                point.id.toString(), point.name, point.adress, point.latitude, point.longitude, point.contactPerson
            )
        )
    }

    override fun addPoint(requestBody: PointRequest): ResponseEntity<PointAnswer> {
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

    override fun updatePoint(pointId: String, requestBody: PointRequest): ResponseEntity<PointAnswer> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { ChangeSetPersister.NotFoundException() }

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

    override fun deletePoint(pointId: String): ResponseEntity<Unit> {
        val point: Point = pointRepository.findById(pointId).orElseThrow { ChangeSetPersister.NotFoundException() }
        pointRepository.delete(point)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return ResponseEntity.noContent().build()

    }
}