package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.repository.PositionRepository
import com.example.petproject.services.PositionService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class PositionServiceImpl(val positionRepository: PositionRepository) : PositionService {


    override fun getAllPositions(): Flux<PositionAnswer> {
        return positionRepository.findAll()
            .map { e -> PositionAnswer(e.id.toString(), e.description, e.comment) }.delayElements(Duration.ofSeconds(1))
    }

    override fun deleteAllPositions(): Mono<Void> {
       return positionRepository.deleteAll()
    }
}