package com.example.petproject.controllers.entityControllers

import com.example.petproject.answers.PositionAnswer
import com.example.petproject.repository.PositionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class PositionController {
    @Autowired
    private val positionRepository: PositionRepository? = null

    @GetMapping("/positions")
    fun getAllPositions(): List<PositionAnswer> {
        val temp = mutableListOf<PositionAnswer>()
        positionRepository!!.findAll()
            .forEach { e -> temp.add(PositionAnswer(e.id.toString(), e.description, e.comment)) }

        return temp

    }

    @DeleteMapping("/positions")
    fun deleteAllPositions(): Map<String, Boolean> {
        positionRepository?.deleteAll()
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PositionController::class.java)
    }
}