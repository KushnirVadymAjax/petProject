package com.example.petproject.controllers.entityControllers

import com.example.petproject.model.Consumables
import com.example.petproject.repository.ConsumablesRepository
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
class ConsumablesController {
    @Autowired
    private val consumablesRepository: ConsumablesRepository? = null

    @GetMapping("/consumables")
    fun getAllConsumables(request: HttpServletRequest): List<Consumables> {
        return consumablesRepository!!.findAll()
    }

    @DeleteMapping("/consumables")
    fun deleteConsumable(request: HttpServletRequest): Map<String, Boolean> {
        consumablesRepository?.deleteAll()
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ConsumablesController::class.java)
    }
}