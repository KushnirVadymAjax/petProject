package com.example.petproject.jsonMapping.answers

import org.springframework.format.annotation.NumberFormat

class PointAnswer(
    val id: String,
    var name: String,
    var adress: String = "",
    @NumberFormat(pattern = "##,##0.00000")
    var latitude: Double = 0.0,
    @NumberFormat(pattern = "##,##0.00000")
    var longitude: Double = 0.0,
    var contactPerson: String = "",
    var contactNumber: String = "",
    var comment: String = ""
)