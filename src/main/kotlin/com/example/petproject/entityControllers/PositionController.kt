package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.services.PositionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1")
class PositionController(val positionService: PositionService) {

    @GetMapping("/positions")
    @ResponseStatus(HttpStatus.OK)
    fun getAllPositions(): Flux<PositionAnswer> {
        return positionService.getAllPositions()
    }

    @DeleteMapping("/positions")
    @ResponseStatus(HttpStatus.OK)
    fun deleteAllPositions(): Mono<Void> {
        return positionService.deleteAllPositions()
    }
}