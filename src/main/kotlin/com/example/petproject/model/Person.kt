package com.example.petproject.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Person(
    @Id
    val id: ObjectId = ObjectId.get(),
    var name: String,
    var fullName: String = "",
    var phoneNumber: String = "",
    val admin: Boolean = false
)