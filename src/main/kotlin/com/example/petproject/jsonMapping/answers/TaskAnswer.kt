package com.example.petproject.jsonMapping.answers

import java.time.LocalDate

class TaskAnswer(
    var id: String,
    val date: LocalDate,
    val pointID: String,
    val positions: List<PositionAnswer>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TaskAnswer

        if (date != other.date) return false
        if (pointID != other.pointID) return false
        if (positions != other.positions) return false

        return true
    }

    override fun hashCode(): Int {
        var result = date.hashCode()
        result = 31 * result + pointID.hashCode()
        result = 31 * result + positions.hashCode()
        return result
    }
}