package com.api.delivery.repository

import com.api.delivery.model.Delivery
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface DeliveryRepository: MongoRepository<Delivery, ObjectId> {

    fun findAllByIdClient(idCleint: Long): List<Delivery>
    fun findByIdAndIdClient(id: ObjectId, idCleint: Long): Optional<Delivery>
}