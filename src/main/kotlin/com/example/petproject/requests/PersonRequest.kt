package com.example.petproject.requests

class PersonRequest(
    var name: String,
    var fullName: String = "",
    var phoneNumber: String = "",
    val admin: Boolean = false
)