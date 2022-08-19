package com.api.delivery.consumer.listner

import com.api.delivery.enuns.StatusDelivery
import com.api.delivery.model.Delivery
import com.api.delivery.model.Product
import com.api.delivery.service.DeliveryService
import com.api.purchases.controller.response.PurchasesResponse
import com.google.gson.Gson
import org.bson.types.ObjectId
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class RabbitMQListener(
    private var deliveryService: DeliveryService){


    @RabbitListener(queues = ["PURCHASES-MODEL-QUEUE"])
    fun receiveUserModelMessage(message : Message){

            val body = message.body?.let { String(it) }

            var purchasesResponse: PurchasesResponse = Gson().fromJson(body, PurchasesResponse::class.java)
        println(purchasesResponse)

            deliveryService.saveDelivery(
                Delivery(
                    ObjectId.get(),
                    purchasesResponse.idClient,
                    StatusDelivery.SHIPP,
                    listOf<Product>(
                        Product(
                            purchasesResponse.product.idProduct,
                            purchasesResponse.product.qtd
                        )
                    ),
                    purchasesResponse.addressDelivery,
                )
            )
    }
}