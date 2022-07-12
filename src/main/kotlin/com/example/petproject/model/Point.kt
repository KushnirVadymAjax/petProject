package com.example.petproject.model

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.format.annotation.NumberFormat

@Document
data class Point(
    @Id
    val id: ObjectId = ObjectId.get(),
    var name: String = "",
    var adress: String = "",
    @NumberFormat(pattern = "##,##0.00000")
    var latitude: Double = 0.0,
    @NumberFormat(pattern = "##,##0.00000")
    var longitude: Double = 0.0,
    var contactPerson: String = "",
    var contactNumber: String = "",
    var comment: String = ""
)