package com.example.petproject.jsonMapping.requests

import java.time.LocalDate

class TaskRequest(
    val date: LocalDate,
    val pointID: String = "",
    val positions: List<PositionRequest> = mutableListOf()
)