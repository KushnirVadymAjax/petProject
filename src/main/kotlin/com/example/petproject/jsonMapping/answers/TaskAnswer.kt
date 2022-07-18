package com.example.petproject.jsonMapping.answers

import java.time.LocalDate

class TaskAnswer(
    val id: String,
    val date: LocalDate,
    val pointID: String,
    val positions: List<PositionAnswer>
)