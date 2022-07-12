package com.example.petproject.controllers.entityControllers

import com.example.petproject.answers.PersonAnswer
import com.example.petproject.model.Person
import com.example.petproject.repository.PersonRepository
import com.example.petproject.requests.PersonRequest
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1")
class PersonController {
    @Autowired
    private val personsRepository: PersonRepository? = null

    @GetMapping("/persons")
    fun getAllPersons(): List<PersonAnswer> {

        val temp = mutableListOf<PersonAnswer>()
        personsRepository!!.findAll()
            .forEach { e -> temp.add(PersonAnswer(e.id.toString(), e.name, e.fullName, e.phoneNumber, e.admin)) }

        return temp
    }

    @GetMapping("/person/{id}")
    fun getPersonById(@PathVariable(value = "id") personId: String): ResponseEntity<PersonAnswer> {
        val person: Person = personsRepository!!.findById(personId).orElseThrow { NotFoundException() }

        return ResponseEntity.ok().body<PersonAnswer>(
            PersonAnswer(
                person.id.toString(), person.name, person.fullName, person.phoneNumber, person.admin
            )
        )
    }

    @PostMapping("/person")
    fun addPerson(@Validated @RequestBody requestBody: Person): ResponseEntity<PersonAnswer> {
        LOGGER.info("Request body:$requestBody")
        return try {
            val person: Person = personsRepository!!.save(requestBody)
            return ResponseEntity.ok().body<PersonAnswer>(
                PersonAnswer(
                    person.id.toString(), person.name, person.fullName, person.phoneNumber, person.admin
                )
            )
        } catch (e: Exception) {
            LOGGER.error(Arrays.toString(e.stackTrace))
            ResponseEntity<PersonAnswer>(null, HttpStatus.BAD_GATEWAY)
        }
    }

    @PutMapping("/person/{id}")
    fun updatePerson(
        @PathVariable(value = "id") personId: String,
        @Validated @RequestBody personNew: PersonRequest
    ): ResponseEntity<PersonAnswer> {

        LOGGER.info("Request body:$personNew")
        val person: Person = personsRepository!!.findById(personId).orElseThrow { NotFoundException() }
        person.fullName = personNew.fullName
        person.name = personNew.name
//        person.setManager(personNew.getManager())
        person.phoneNumber = personNew.phoneNumber
        val updatedTask: Person = personsRepository.save(person)
        return ResponseEntity.ok<PersonAnswer>(
            PersonAnswer(
                updatedTask.id.toString(),
                updatedTask.name,
                updatedTask.fullName,
                updatedTask.phoneNumber,
                updatedTask.admin
            )
        )
    }

    @DeleteMapping("/person/{id}")
    fun deletePerson(@PathVariable(value = "id") personId: String): Map<String, Boolean> {

        val person: Person = personsRepository!!.findById(personId).orElseThrow { NotFoundException() }
        personsRepository.delete(person)
        val response: MutableMap<String, Boolean> = HashMap()
        response["deleted"] = java.lang.Boolean.TRUE
        return response
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(PersonController::class.java)
    }
}