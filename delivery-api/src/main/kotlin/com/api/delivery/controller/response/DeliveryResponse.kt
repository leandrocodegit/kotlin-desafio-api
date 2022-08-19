package com.api.delivery.controller.response

import com.api.delivery.enuns.StatusDelivery
import com.api.delivery.model.Product
import org.bson.types.ObjectId

class DeliveryResponse(
    var id: String,
    var idClient: Long,
    var status: StatusDelivery,
    var products: List<ProductResponse>,
    var addressDelivery: String
)