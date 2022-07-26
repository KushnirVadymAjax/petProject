package com.example.petproject.mongoTemplate

import com.example.petproject.model.Task
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.find
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDate


@Repository
class TaskCustomRepositoryImpl(var mongoTemplate: ReactiveMongoTemplate):TaskCustomRepository{
    override fun findTasksAfterDate(date: LocalDate): Flux<Task> {
        val query = Query()
        query.addCriteria(Criteria.where("date").gte(date))
        return mongoTemplate.find<Task>(query)
    }

}