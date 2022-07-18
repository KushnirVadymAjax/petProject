package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.repository.PositionRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PositionController(private val positionRepository: PositionRepository) {

    @GetMapping("/positions")
    fun getAllPositions(): ResponseEntity<List<PositionAnswer>> {
        val temp = mutableListOf<PositionAnswer>()
        positionRepository.findAll()
            .forEach { e -> temp.add(PositionAnswer(e.id.toString(), e.description, e.comment)) }
        return ResponseEntity.ok(temp)

    }

    @DeleteMapping("/positions")
    fun deleteAllPositions(): ResponseEntity<Unit> {
        positionRepository.deleteAll()
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return ResponseEntity.noContent().build()
    }
}