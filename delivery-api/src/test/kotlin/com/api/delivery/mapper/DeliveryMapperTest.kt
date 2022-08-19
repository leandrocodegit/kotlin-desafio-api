package com.api.delivery.mapper

import com.api.delivery.build.Builder
import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.controller.request.ProductRequest
import com.api.delivery.controller.response.DeliveryResponse
import com.api.delivery.controller.response.ProductResponse
import com.api.delivery.model.Delivery
import com.api.delivery.model.Product
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeliveryMapperTest {

    @Autowired
    private lateinit var deliveryMapper: DeliveryMapper

    private lateinit var delivery: Delivery
    private lateinit var deliveryRequest: DeliveryRequest
    private lateinit var deliveryResponse: DeliveryResponse

    @BeforeEach
    fun setup(){
        delivery = Builder.createDelivery()
        deliveryRequest = Builder.createDeliveryRequest()
    }

    @Test
    fun `test mapper request to entity`(){
        var newDelivery: Delivery = deliveryMapper.toEntity(deliveryRequest)
        assert(newDelivery.idClient == deliveryRequest.idClient)
        assert(newDelivery.products.size == deliveryRequest.products.size)

        var newProductRequest: ProductRequest = deliveryRequest.products.first()
        var newProduct: Product = delivery.products.first()
        assert(newProductRequest.idProduct == newProduct.idProduct)
        assert(newProductRequest.qtd == newProduct.qtd)
    }

    @Test
    fun `test mapper entity to response`(){
        var newResponse: DeliveryResponse = deliveryMapper.toResponse(delivery)
        assert(newResponse.id == delivery.id.toString())
        assert(newResponse.idClient == delivery.idClient)
        assert(newResponse.status == delivery.status)
        assert(newResponse.addressDelivery == delivery.addressDelivery)
        assert(newResponse.products.size == delivery.products.size)

        var newProductResponse: ProductResponse = newResponse.products.first()
        var newProduct: Product = delivery.products.first()
        assert(newProductResponse.idProduct == newProduct.idProduct)
        assert(newProductResponse.qtd == newProduct.qtd)
    }
}