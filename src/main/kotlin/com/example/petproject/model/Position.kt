package com.example.petproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Position(
    @Id
    val id: ObjectId = ObjectId.get(),
    val description: String,
    val comment: String = ""
)