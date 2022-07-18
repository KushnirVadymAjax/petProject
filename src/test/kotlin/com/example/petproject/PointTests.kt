package com.example.petproject

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.jsonMapping.requests.TaskRequest
import com.example.petproject.model.Point
import com.example.petproject.model.Task
import com.example.petproject.repository.PointRepository
import com.example.petproject.repository.PositionRepository
import com.example.petproject.repository.TaskRepository
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate
import java.util.NoSuchElementException

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PointTests @Autowired constructor(
    private val restTemplate: TestRestTemplate,
    private val pointRepository: PointRepository
) {

    private val defaultPointId = ObjectId.get()

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        pointRepository.deleteAll()
    }

    private fun getRootUrl(): String? = "http://localhost:$port/api/v1/points"

    private fun saveOnePoint() = pointRepository.save(Point(defaultPointId))

    private fun preparePointRequest(): PointRequest {
        pointRepository.save(Point(defaultPointId))
        return PointRequest(defaultPointId.toString(),"adders")
    }

    @Test
    fun `should return all points`() {
        saveOnePoint()

        val response = restTemplate.getForEntity(
            getRootUrl(), List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun `should return single point by id`() {
        saveOnePoint()

        val response = restTemplate.getForEntity(
            getRootUrl() + "/$defaultPointId", Task::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(defaultPointId, response.body?.id)
    }

    @Test
    fun `should create new point`() {
        val pointRequest = preparePointRequest()

        val response = restTemplate.postForEntity(
            getRootUrl(), pointRequest, PointAnswer::class.java
        )


        assertEquals(201, response.statusCode.value())
        assertNotNull(response.body)
        assertNotNull(response.body?.id)
    }

    @Test
    fun `should update existing task`() {
        saveOnePoint()
        val pointRequest = preparePointRequest()

        val updateResponse = restTemplate.exchange(
            getRootUrl() + "/$defaultPointId",
            HttpMethod.PUT,
            HttpEntity(pointRequest, HttpHeaders()),
            PointAnswer::class.java
        )
        val updatedPoint = pointRepository.findById(defaultPointId).get()

        assertEquals(200, updateResponse.statusCode.value())
        assertEquals(defaultPointId, updatedPoint.id)
        assertEquals(pointRequest.adress,updatedPoint.adress)

    }

    @Test
    fun `should delete existing task`() {
        saveOnePoint()

        val delete = restTemplate.exchange(
            getRootUrl() + "/$defaultPointId",
            HttpMethod.DELETE,
            HttpEntity(null, HttpHeaders()),
            ResponseEntity::class.java
        )

        assertEquals(204, delete.statusCode.value())
        assertThrows(NoSuchElementException::class.java) { pointRepository.findById(defaultPointId).get() }
    }


}
