package com.example.petproject.utils

import com.example.petproject.jsonMapping.answers.PositionAnswer
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.model.Position

class PositionUtils {
    companion object {
        fun convertPositionsToPositionAnswer(positions: List<Position>): MutableList<PositionAnswer> {
            val temp = mutableListOf<PositionAnswer>()
            for (pos in positions) {
                val tempPosition =
                    PositionAnswer(id = pos.id.toString(), description = pos.description, comment = pos.comment)
                temp.add(tempPosition)
            }
            return temp
        }

        fun convertPositionToPositionAnswer(position: Position): PositionAnswer {
            return PositionAnswer(position.id.toString(), position.description, position.comment)
        }

        fun convertPositionRequestToPosition(positions: List<PositionRequest>): MutableList<Position> {

            val temp = mutableListOf<Position>()
            for (pos in positions) {
                val tempPosition = Position(description = pos.description, comment = pos.comment)
                temp.add(tempPosition)
            }
            return temp
        }
    }
}