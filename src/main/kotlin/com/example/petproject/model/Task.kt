package com.example.petproject

import java.time.LocalDateTime

@Document
data class Patient (
    @Id
    val id: ObjectId = ObjectId.get(),
    val name: String,
    val description: String,
    val createdDate: LocalDateTime = LocalDateTime.now(),
    val modifiedDate: LocalDateTime = LocalDateTime.now()
)