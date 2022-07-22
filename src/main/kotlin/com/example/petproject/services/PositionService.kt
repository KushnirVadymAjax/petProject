package com.example.petproject.services

import com.example.petproject.jsonMapping.answers.PositionAnswer
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

interface PositionService {
    fun getAllPositions(): ResponseEntity<List<PositionAnswer>>

    fun deleteAllPositions(): ResponseEntity<Unit>
}