package com.example.petproject

import com.example.petproject.jsonMapping.answers.TaskAnswer
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
class PositionTests @Autowired constructor(
    private val taskRepository: TaskRepository,
    private val restTemplate: TestRestTemplate,
    private val positionRepository: PositionRepository
) {
    private val defaultTaskId = ObjectId.get()
    private val defaultPointId = ObjectId.get()

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        positionRepository.deleteAll()
    }

    private fun getRootUrl(): String? = "http://localhost:$port/api/v1/positions"

    private fun saveOneTask() = taskRepository.save(Task(defaultTaskId, LocalDate.now()))


    @Test
    fun `should return all positions`() {


        val response = restTemplate.getForEntity(
            getRootUrl(), List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
    }

    @Test
    fun `should delete all positions`() {


        val response = restTemplate.exchange(
            getRootUrl(),
            HttpMethod.DELETE,
            HttpEntity(null, HttpHeaders()),
            ResponseEntity::class.java
        )

        assertEquals(204, response.statusCode.value())
    }
}
