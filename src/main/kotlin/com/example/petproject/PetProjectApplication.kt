package com.example.petproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@SpringBootApplication
class PetProjectApplication

fun main(args: Array<String>) {
    runApplication<PetProjectApplication>(*args)
}


