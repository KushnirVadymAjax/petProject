package com.example.petproject.controllers.entityControllers

import com.example.petproject.model.Position
import com.example.petproject.repository.PositionRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1")
class PositionController {
    @Autowired
    private val positionRepository: PositionRepository? = null

    @GetMapping("/positions")
    fun getAllPositions(request: HttpServletRequest): List<Position> {
        return positionRepository!!.findAll()
    }

    @DeleteMapping("/positions")
    fun deleteAllPositions(request: HttpServletRequest): Map<String, Boolean> {
        positionRepository?.deleteAll()
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PositionController::class.java)
    }
}