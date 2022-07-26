package com.example.petproject.mongoTemplate

import com.example.petproject.model.Task
import reactor.core.publisher.Flux
import java.time.LocalDate

interface TaskCustomRepository {

    fun findTasksAfterDate(date:LocalDate): Flux<Task>

}