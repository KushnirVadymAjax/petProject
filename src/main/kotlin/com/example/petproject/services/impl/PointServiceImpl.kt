package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.services.PointService
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@Service
class PointServiceImpl(val pointRepository: PointRepository) : PointService {

    override fun getAllPoints(): Flux<PointAnswer> {

        return pointRepository.findAll()
            .map { e ->
                PointAnswer(
                    e.id.toString(), e.name, e.adress, e.latitude, e.longitude, e.contactPerson
                )
            }.delayElements(Duration.ofSeconds(1))
    }


    override fun getPointById(pointId: ObjectId): Mono<PointAnswer> =
        pointRepository.findById(pointId).map { e ->
            PointAnswer(
                e.id.toString(), e.name, e.adress, e.latitude, e.longitude, e.contactPerson
            )
        }.switchIfEmpty {
            Mono.error(
                ChangeSetPersister.NotFoundException()
            )
        }

    override fun addPoint(requestBody: PointRequest): Mono<PointAnswer> {
        val point = Point(
            name = requestBody.name,
            adress = requestBody.adress,
            comment = requestBody.comment,
            contactNumber = requestBody.contactNumber,
            contactPerson = requestBody.contactPerson,
            latitude = requestBody.latitude,
            longitude = requestBody.longitude
        )
        return pointRepository.save(point).map { e ->
            PointAnswer(
                e.id.toString(), e.name, e.adress, e.latitude, e.longitude, e.contactPerson
            )
        }
    }

    override fun updatePoint(pointId: ObjectId, requestBody: PointRequest): Mono<PointAnswer> {
        val point = pointRepository.findById(pointId)



        return point.flatMap {
            pointRepository.save(it.apply {
                it.adress = requestBody.adress
                it.comment = requestBody.comment
                it.contactNumber = requestBody.contactNumber
                it.name = requestBody.name
                it.longitude = requestBody.longitude
                it.latitude = requestBody.latitude
                it.contactPerson = requestBody.contactPerson
            })
        }.map { e ->
            PointAnswer(
                e.id.toString(), e.name, e.adress, e.latitude, e.longitude, e.contactPerson
            )
        }
    }

    override fun deletePoint(pointId: ObjectId): Mono<Void> {

        return pointRepository.findById(pointId)
            .switchIfEmpty {
                Mono.error(
                    ChangeSetPersister.NotFoundException()
                )
            }
            .flatMap(pointRepository::delete)


    }
}