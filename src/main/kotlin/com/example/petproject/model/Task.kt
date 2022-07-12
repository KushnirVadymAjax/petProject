package com.example.petproject.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.util.*

@Document
data class Task(
    @Id
    val id: ObjectId = ObjectId.get(),
    var date: LocalDate,
    @DBRef
    var point: Point = Point(),
    @DBRef
    var positions: List<Position>,
    @DBRef
    var consumables: List<Consumables>,
    @DBRef
    var persons: List<Person>
)