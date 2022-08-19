package com.api.delivery.mapper

import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.controller.response.DeliveryResponse
import com.api.delivery.model.Delivery
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface DeliveryMapper {

    @Mappings(
        Mapping(target = "addressDelivery", ignore = true),
        Mapping(target = "status", expression = "java(StatusDelivery.OPEN)"),
        Mapping(target = "id", expression = "java(ObjectId.get())")
    )
    fun toEntity(deliveryRequest: DeliveryRequest): Delivery
    @Mapping(target = "id", expression = "java(delivery.getId().toString())")
    fun toResponse(delivery: Delivery): DeliveryResponse


}