package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.PositionAnswer
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PositionService {
    fun getAllPositions(): Flux<PositionAnswer>

    fun deleteAllPositions(): Mono<Void>
}