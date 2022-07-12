package com.example.petproject.controllers.entityControllers


import com.example.petproject.model.Person
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.requests.PointRequest
import org.slf4j.LoggerFactory
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1")
class PointController {
    @Autowired
    private val pointRepository: PointRepository? = null

    @GetMapping("/points")
    fun getAllPoints(request: HttpServletRequest): List<Point> {
        return pointRepository!!.findAll()
    }

    @GetMapping("/point/{id}")
    fun getPointById(@PathVariable(value = "id") pointId: String, request: HttpServletRequest): ResponseEntity<Point> {
        val point: Point = pointRepository!!.findById(pointId)
            .orElseThrow { NotFoundException() }
        return ResponseEntity.ok().body<Point>(point)
    }

    @PostMapping("/point")
    fun addPoint(
        @Validated @RequestBody requestBody: PointRequest,
        request: HttpServletRequest
    ): ResponseEntity<Point> {

        LOGGER.info("Request body:$requestBody")
        val point: Point = Point(
            name = requestBody.name,
            adress = requestBody.adress,
            comment = requestBody.comment,
            contactNumber = requestBody.contactNumber,
            contactPerson = requestBody.contactPerson,
            latitude = requestBody.latitude,
            longitude = requestBody.longitude
        )
        return try {
            pointRepository!!.save(point)
            ResponseEntity<Point>(null, HttpStatus.OK)
        } catch (e: Exception) {
            LOGGER.error(Arrays.toString(e.stackTrace))
            ResponseEntity<Point>(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/point/{id}")
    fun updatePoint(
        @PathVariable(value = "id") pointId: String,
        @Validated @RequestBody requestBody: PointRequest
    ): ResponseEntity<Point> {

        LOGGER.info("Request body:$requestBody")
        val point: Point = pointRepository!!.findById(pointId)
            .orElseThrow { NotFoundException() }
        point.adress = requestBody.adress
        point.comment = requestBody.comment
        point.contactNumber = requestBody.contactNumber
        point.name = requestBody.name
        point.longitude = requestBody.longitude
        point.latitude = requestBody.latitude
        point.contactPerson = requestBody.contactPerson
        val updatedPoint: Point = pointRepository.save(point)
        return ResponseEntity.ok<Point>(updatedPoint)
    }

    @DeleteMapping("/point/{id}")
    fun deletePoint(@PathVariable(value = "id") pointId: String): Map<String, Boolean> {

        val point: Point = pointRepository!!.findById(pointId)
            .orElseThrow { NotFoundException() }
        pointRepository.delete(point)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PointController::class.java)
    }
}