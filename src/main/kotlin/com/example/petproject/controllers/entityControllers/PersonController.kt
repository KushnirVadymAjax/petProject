package com.example.petproject.controllers.entityControllers

import com.example.petproject.model.Person
import com.example.petproject.repository.PersonRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/v1")
class PersonController {
    @Autowired
    private val personsRepository: PersonRepository? = null

    @GetMapping("/persons")
    fun getAllPersons(request: HttpServletRequest): List<Person> {
        return personsRepository!!.findAll()
    }

    @GetMapping("/person/{id}")
    fun getPersonById(
        @PathVariable(value = "id") personId: String,
        request: HttpServletRequest
    ): ResponseEntity<Person> {
        val person: Person = personsRepository!!.findById(personId)
            .orElseThrow { NotFoundException() }

        return ResponseEntity.ok().body<Person>(person)
    }

    @PostMapping("/person")
    fun addPerson(@Validated @RequestBody requestBody: Person, request: HttpServletRequest): ResponseEntity<Person> {
        LOGGER.info("Request body:$requestBody")
        return try {
            val person: Person = personsRepository!!.save(requestBody)
            ResponseEntity<Person>(null, HttpStatus.OK)
        } catch (e: Exception) {
            LOGGER.error(Arrays.toString(e.stackTrace))
            ResponseEntity<Person>(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/person/{id}")
    fun updatePerson(
        @PathVariable(value = "id") personId: String,
        @Validated @RequestBody personNew: Person, request: HttpServletRequest
    ): ResponseEntity<Person> {

        LOGGER.info("Request body:$personNew")
        val person: Person = personsRepository!!.findById(personId)
            .orElseThrow { NotFoundException() }
        person.fullName = personNew.fullName
        person.name = personNew.name
//        person.setManager(personNew.getManager())
        person.phoneNumber = personNew.phoneNumber
        val updatedTask: Person = personsRepository.save(person)
        return ResponseEntity.ok<Person>(updatedTask)
    }

    @DeleteMapping("/person/{id}")
    fun deletePerson(@PathVariable(value = "id") personId: String, request: HttpServletRequest): Map<String, Boolean> {

        val person: Person = personsRepository!!.findById(personId)
            .orElseThrow { NotFoundException() }
        personsRepository.delete(person)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PersonController::class.java)
    }
}