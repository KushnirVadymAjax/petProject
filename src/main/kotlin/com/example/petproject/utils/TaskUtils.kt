package com.example.petproject.utils

import com.example.petproject.jsonMapping.answers.TaskAnswer
import com.example.petproject.jsonMapping.requests.PositionRequest
import com.example.petproject.model.Position
import com.example.petproject.model.Task

class TaskUtils() {
    companion object {
        fun convertPositionRequestToPosition(positions: List<PositionRequest>): MutableList<Position> {

            val temp = mutableListOf<Position>()
            for (pos in positions) {
                val tempPosition = Position(description = pos.description, comment = pos.comment)
                temp.add(tempPosition)
            }
            return temp
        }

        fun convertTaskToTaskAnswer(task: Task): TaskAnswer {
            return TaskAnswer(
                task.id.toString(),
                task.date,
                task.point?.id.toString(),
                PositionUtils.convertPositionsToPositionAnswer(task.positions)
            )
        }

    }
}