package com.example.petproject


import com.example.petproject.entityControllers.PointController
import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.services.impl.PointServiceImpl
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import org.mockito.internal.verification.VerificationModeFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.http.HttpHeaders
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux

@WebFluxTest(PointController::class)
@ExtendWith(SpringExtension::class)
@Import(PointServiceImpl::class)
class PointTests {
    @MockBean
    lateinit var pointRepository: PointRepository

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should return all points`() {
        val fPoint = Point(ObjectId.get(), "fPoint", "adress fPoint", 43.124, 32.123, "contact person fPoint")
        val sPoint = Point(ObjectId.get(), "sPoint", "adress sPoint", 43.124, 32.123, "contact person sPoint")

        val pointList = listOf(fPoint, sPoint).toFlux()

        Mockito.`when`(pointRepository.findAll()).thenReturn(pointList)

        webClient.get().uri("http://localhost:8080/api/v1/points").header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(PointAnswer::class.java)

        Mockito.verify(pointRepository, VerificationModeFactory.times(1)).findAll()
    }

    @Test
    fun `should return single point by id`() {
        val objectId = ObjectId.get()

        val expected = Point(objectId, "name1", "adress1", 23.333, 42.12312, contactPerson = "contactPerson1", contactNumber = "contactNumber1", comment = "comment1")

        Mockito.`when`(pointRepository.findById(objectId)).thenReturn(Mono.just(expected))

        webClient.get().uri("http://localhost:8080/api/v1/points/{id}", objectId)
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange().expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.adress").isEqualTo(expected.adress)
            .jsonPath("$.latitude").isEqualTo(expected.latitude)
            .jsonPath("$.longitude").isEqualTo(expected.longitude)
            .jsonPath("$.contactPerson").isEqualTo(expected.contactPerson)
            .jsonPath("$.contactNumber").isEqualTo(expected.contactNumber)
            .jsonPath("$.comment").isEqualTo(expected.comment)


        Mockito.verify(pointRepository).findById(objectId)
    }

    @Test
    fun `should create new point`() {
        val objectId = ObjectId.get()

        val expected = Point(objectId, "name1", "adress1", 23.333, 42.12312, contactPerson = "contactPerson1", contactNumber = "contactNumber1", comment = "comment1")

        Mockito.`when`(pointRepository.save(any(Point::class.java))).thenReturn(Mono.just(expected))


        webClient.post().uri("http://localhost:8080/api/v1/points").header(HttpHeaders.ACCEPT, "application/json")
            .body(Mono.just(expected), PointRequest::class.java)
            .exchange().expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.adress").isEqualTo(expected.adress)
            .jsonPath("$.latitude").isEqualTo(expected.latitude)
            .jsonPath("$.longitude").isEqualTo(expected.longitude)
            .jsonPath("$.contactPerson").isEqualTo(expected.contactPerson)
            .jsonPath("$.contactNumber").isEqualTo(expected.contactNumber)
            .jsonPath("$.comment").isEqualTo(expected.comment)
    }

    @Test
    fun `should update existing point`() {
        val objectId = ObjectId.get()

        val request = Point(objectId, "name1", "adress1", 23.333, 42.12312, contactPerson = "contactPerson1", contactNumber = "contactNumber1", comment = "comment1")
        val expected = Point(objectId, "change name1", "change adress1", 23.333, 42.12312, contactPerson = "change contactPerson1", contactNumber = "change contactNumber1", comment = "change comment1")

        Mockito.`when`(pointRepository.save(any(Point::class.java))).thenReturn(Mono.just(expected))

        Mockito.`when`(pointRepository.findById(objectId)).thenReturn(Mono.just(request))

        webClient.put().uri("http://localhost:8080/api/v1/points/{id}",objectId).header(HttpHeaders.ACCEPT, "application/json")
            .body(Mono.just(request), PointRequest::class.java)
            .exchange().expectStatus().isOk
            .expectBody()
            .jsonPath("$.id").isEqualTo(objectId.toString())
            .jsonPath("$.adress").isEqualTo(expected.adress)
            .jsonPath("$.latitude").isEqualTo(expected.latitude)
            .jsonPath("$.longitude").isEqualTo(expected.longitude)
            .jsonPath("$.contactPerson").isEqualTo(expected.contactPerson)
            .jsonPath("$.contactNumber").isEqualTo(expected.contactNumber)
            .jsonPath("$.comment").isEqualTo(expected.comment)
    }

    @Test
    fun `should delete existing point`() {
        val objectId = ObjectId.get()
        val voidReturn = Mono.empty<Void>()
        Mockito.`when`(pointRepository.deleteById(objectId)).thenReturn(voidReturn)

        webClient.delete().uri("http://localhost:8080/api/v1/points/{id}", objectId).exchange().expectStatus().isOk
    }


}
