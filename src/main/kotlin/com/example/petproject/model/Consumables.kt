package com.example.petproject.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Consumables(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val description: String = "",
    val comment: String = ""
)
