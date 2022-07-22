package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.repository.PositionRepository
import com.example.petproject.services.PositionService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PositionServiceImpl(val positionRepository: PositionRepository) : PositionService {


    override fun getAllPositions(): ResponseEntity<List<PositionAnswer>> {
        val temp = mutableListOf<PositionAnswer>()
        positionRepository.findAll().map { e -> temp.add(PositionAnswer(e.id.toString(), e.description, e.comment)) }

        return ResponseEntity.ok(temp)
    }

    override fun deleteAllPositions(): ResponseEntity<Unit> {
        positionRepository.deleteAll()
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return ResponseEntity.noContent().build()
    }
}