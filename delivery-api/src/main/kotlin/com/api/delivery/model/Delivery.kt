package com.api.delivery.model

import com.api.delivery.enuns.StatusDelivery
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Delivery(
    @Id
    var id: ObjectId,
    var idClient: Long,
    var status: StatusDelivery,
    var products: List<Product>,
    var addressDelivery: String?)