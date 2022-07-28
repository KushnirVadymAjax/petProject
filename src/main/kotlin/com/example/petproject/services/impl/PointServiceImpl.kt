package com.example.petproject.services.impl

import com.example.petproject.jsonMapping.answers.PointAnswer
import com.example.petproject.jsonMapping.requests.PointRequest
import com.example.petproject.model.Point
import com.example.petproject.repository.PointRepository
import com.example.petproject.services.PointService
import com.example.petproject.utils.PointUtils
import org.bson.types.ObjectId
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import java.time.Duration

@Service
class PointServiceImpl(val pointRepository: PointRepository) : PointService {

    override fun getAllPoints(): Flux<PointAnswer> {
        return pointRepository.findAll().map {
            PointUtils.convertPointToPointAnswer(it)
        }
    }


    override fun getPointById(pointId: ObjectId): Mono<PointAnswer> = pointRepository.findById(pointId).map {
        PointUtils.convertPointToPointAnswer(it)
    }.switchIfEmpty {
        Mono.error(
            NotFoundException()
        )
    }

    override fun addPoint(requestBody: PointRequest): Mono<PointAnswer> {
        return pointRepository.save(
            Point(
                name = requestBody.name,
                adress = requestBody.adress,
                comment = requestBody.comment,
                contactNumber = requestBody.contactNumber,
                contactPerson = requestBody.contactPerson,
                latitude = requestBody.latitude,
                longitude = requestBody.longitude
            )
        ).map {
            PointUtils.convertPointToPointAnswer(it)
        }
    }

    override fun updatePoint(pointId: ObjectId, requestBody: PointRequest): Mono<PointAnswer> {

        return pointRepository.findById(pointId).switchIfEmpty {
            Mono.error(
                NotFoundException()
            )
        }.then(
            pointRepository.save(
                Point(
                    id = pointId,
                    name = requestBody.name,
                    adress = requestBody.adress,
                    comment = requestBody.comment,
                    contactNumber = requestBody.contactNumber,
                    contactPerson = requestBody.contactPerson,
                    latitude = requestBody.latitude,
                    longitude = requestBody.longitude
                )
            )
        ).map { PointUtils.convertPointToPointAnswer(it) }
    }

    override fun deletePoint(pointId: ObjectId): Mono<Void> {
        return pointRepository.deleteById(pointId)
    }
}