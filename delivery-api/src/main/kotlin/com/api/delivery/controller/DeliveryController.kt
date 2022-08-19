package com.api.delivery.controller

import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.controller.response.DeliveryResponse
import com.api.delivery.mapper.DeliveryMapper
import com.api.delivery.model.Delivery
import com.api.delivery.service.DeliveryService
import org.bson.types.ObjectId
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/delivery")
class DeliveryController(
    private val deliveryService: DeliveryService,
    private val deliveryMapper: DeliveryMapper
) {

    @GetMapping("/{idClient}")
    fun listAllDeliveryClient(@PathVariable idClient: Long): ResponseEntity<List<DeliveryResponse>>{
        var list: List<Delivery> = deliveryService.listAllDeliveryesClient(idClient)
        return ResponseEntity.ok(list.stream()
            .map { d -> deliveryMapper.toResponse(d) }
            .collect(Collectors.toList()))
    }

    @GetMapping("/{idClient}/{id}")
    fun getDeliveryClient(@PathVariable idClient: Long, @PathVariable id: ObjectId): ResponseEntity<DeliveryResponse>{
        var delivery: Delivery = deliveryService.getDeliveryByClient(id, idClient)
        return ResponseEntity.ok(deliveryMapper.toResponse(delivery))
    }

    @PostMapping
    fun createDelivery(@RequestBody @Valid deliveryRequest: DeliveryRequest): ResponseEntity<DeliveryResponse>{
        return ResponseEntity.ok(
            deliveryMapper.toResponse(
                deliveryService.saveDelivery(
                    deliveryMapper.toEntity(deliveryRequest))))
    }
}