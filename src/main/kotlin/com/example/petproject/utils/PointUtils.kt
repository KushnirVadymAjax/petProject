package com.example.petproject.utils

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.model.Point

class PointUtils {
    companion object {
        fun convertPointToPointAnswer(point: Point): PointAnswer {
            return PointAnswer(
                point.id.toString(), point.name, point.adress, point.latitude, point.longitude, point.contactPerson
            )
        }
    }
}