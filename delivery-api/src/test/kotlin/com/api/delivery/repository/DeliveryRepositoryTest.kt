package com.api.delivery.repository

import com.api.delivery.build.Builder
import com.api.delivery.model.Delivery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DeliveryRepositoryTest {

    @MockK
    private lateinit var deliveryRepository: DeliveryRepository

    private lateinit var delivery: Delivery

    @BeforeEach
    fun setup(){

        delivery = Builder.createDelivery()
        every { deliveryRepository.save(delivery) } returns delivery
        every { deliveryRepository.findAllByIdClient(delivery.idClient) } returns listOf(Builder.createDelivery())
        every { deliveryRepository.findByIdAndIdClient(delivery.id, delivery.idClient) } returns Optional.of(delivery)

    }

    @Test
    fun `test create new delivery`(){
        val saveDelivery: Delivery = deliveryRepository.save(delivery)
        assert(saveDelivery.id == delivery.id)
    }

    @Test
    fun `test list all delivery this client`(){
        val listDelivery: List<Delivery> = deliveryRepository.findAllByIdClient(delivery.idClient)
        assert(listDelivery.isNotEmpty())
    }

    @Test
    fun `test get delivery this client and delivery id`(){
        val findDelivery:  Delivery  = deliveryRepository.findByIdAndIdClient(delivery.id, delivery.idClient).get()
        assert(findDelivery.id == delivery.id)
    }
}