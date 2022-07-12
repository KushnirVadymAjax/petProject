package com.example.petproject.answers

import java.time.LocalDate

class TaskAnswer(
    val id: String,
    val date: LocalDate,
    val pointID: String,
    val positions: List<PositionAnswer>,
    val consumables: List<ConsumablesAnswer>,
    val persons: List<PersonAnswer>
)