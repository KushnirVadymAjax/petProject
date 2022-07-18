package com.example.petproject.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

@Document
data class Task(
    @Id
    val id: ObjectId = ObjectId.get(),
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var date: LocalDate = LocalDate.now(),
    @DBRef
    var point: Point? = null,
    @DBRef
    var positions: MutableList<Position> = mutableListOf()
)