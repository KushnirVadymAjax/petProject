package com.example.petproject.entityControllers

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.services.PositionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PositionController(val positionService: PositionService) {

    @GetMapping("/positions")
    fun getAllPositions(): ResponseEntity<List<PositionAnswer>> {
        return positionService.getAllPositions()

    }

    @DeleteMapping("/positions")
    fun deleteAllPositions(): ResponseEntity<Unit> {
        return positionService.deleteAllPositions()
    }
}