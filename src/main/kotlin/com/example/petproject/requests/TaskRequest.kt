package com.example.petproject.requests

import java.time.LocalDate

class TaskRequest(
    val date: LocalDate,
    val pointID: String = "",
    val positions: List<PositionRequest>,
    val consumables: List<ConsumablesRequest>,
    val persons: List<PersonRequest>
)