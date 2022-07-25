package com.example.petproject

import com.example.petproject.repository.PositionRepository
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PositionTests{
    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var positionRepository: PositionRepository

    @LocalServerPort
    protected var port: Int = 0

    @BeforeEach
    fun setUp() {
        positionRepository.deleteAll()
    }

    private fun getRootUrl(): String = "http://localhost:$port/api/v1/positions"

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
