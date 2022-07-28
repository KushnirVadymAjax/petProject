package com.example.petproject

import com.example.petproject.entityControllers.PositionController
import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.model.Position
import com.example.petproject.repository.PositionRepository
import com.example.petproject.services.impl.PositionServiceImpl
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
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

@WebFluxTest(PositionController::class)
@ExtendWith(SpringExtension::class)
@Import(PositionServiceImpl::class)
class PositionTests {
    @MockBean
    lateinit var positionRepository: PositionRepository

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun `should return all positions`() {
        val fPos = Position(ObjectId.get(), "fPos", "comment fPos")
        val sPos = Position(ObjectId.get(), "sPos", "comment sPos")

        val posList = listOf(fPos, sPos).toFlux()

        Mockito.`when`(positionRepository.findAll())
            .thenReturn(posList)

        webClient.get().uri("http://localhost:8080/api/v1/positions").header(HttpHeaders.ACCEPT, "application/json")
            .exchange().expectStatus().isOk.expectBodyList(PositionAnswer::class.java)

        Mockito.verify(positionRepository, VerificationModeFactory.times(1)).findAll()
    }

    @Test
    fun `should delete all positions`() {
        val voidReturn: Mono<Void> = Mono.empty()
        Mockito.`when`(positionRepository.deleteAll())
            .thenReturn(voidReturn)

        webClient.delete().uri("http://localhost:8080/api/v1/positions").exchange().expectStatus().isOk
    }
}
