package com.example.petproject.repository

import com.example.petproject.model.Person
import org.springframework.data.mongodb.repository.MongoRepository

interface PersonRepository : MongoRepository<Person, String> {
}