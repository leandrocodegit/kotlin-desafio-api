package com.api.delivery.service

import com.api.delivery.build.Builder
import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.enuns.CodeError
import com.api.delivery.exceptions.EntityException
import com.api.delivery.integracao.client.reponse.ClientResponse
import com.api.delivery.integracao.client.rest.RestClient
import com.api.delivery.mapper.DeliveryMapper
import com.api.delivery.model.Delivery
import com.api.delivery.repository.DeliveryRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@ExtendWith(MockKExtension::class)
class DeliveryServiceTest {

    @InjectMockKs
    private lateinit var deliveryService: DeliveryService
    @Autowired
    private lateinit var deliveryMapper: DeliveryMapper
    @MockK
    private lateinit var deliveryRepository: DeliveryRepository
    @MockK
    private lateinit var restClient: RestClient

    private lateinit var delivery: Delivery
    private lateinit var deliveryRequest: DeliveryRequest
    private lateinit var clientResponseActive: ClientResponse
    private lateinit var clientResponseInactive: ClientResponse

    @BeforeEach
    fun setup(){

        delivery = Builder.createDelivery()
        deliveryRequest = Builder.createDeliveryRequest()
        clientResponseActive = Builder.createClientResponseActive()
        clientResponseInactive = Builder.createClientResponseInactive()

        every { deliveryRepository.save(delivery) } returns delivery
        every { restClient.getClient(clientResponseActive.id) } returns clientResponseActive

    }

    @Test
    fun `test create new delivery valid`(){

        var saveDelivery: Delivery = deliveryService.saveDelivery(delivery)
        assert(saveDelivery.id == saveDelivery.id)

    }

    @Test
    fun `test create new delivery invalid`(){

        every { restClient.getClient(clientResponseActive.id) } throws EntityException("Erro", CodeError.REST_ERROR)
        assertThrows<EntityException> { deliveryService.saveDelivery(delivery) }

    }

    @Test
    fun `test create new delivery client inactive`(){

        every { restClient.getClient(clientResponseInactive.id) } returns clientResponseInactive
        assertThrows<EntityException> { deliveryService.saveDelivery(delivery) }

    }

    @Test
    fun `test create new delivery not address delivery`(){

        clientResponseActive = Builder.createClientResponseActiveAddressNotDelivery()
        every { restClient.getClient(clientResponseActive.id) } returns clientResponseActive
        assertThrows<EntityException> { deliveryService.saveDelivery(delivery) }

    }
    @Test
    fun `test create new delivery not address empty`(){

        clientResponseActive = Builder.createClientResponseActiveAddressEmpy()
        every { restClient.getClient(clientResponseActive.id) } returns clientResponseActive
        assertThrows<EntityException> { deliveryService.saveDelivery(delivery) }

    }

    @Test
    fun `test find deliveries client valid`(){

        every { deliveryRepository.findAllByIdClient(deliveryRequest.idClient) } returns listOf(Builder.createDelivery(),Builder.createDelivery())
        assert( deliveryService.listAllDeliveryesClient(deliveryRequest.idClient).isNotEmpty() )

    }

    @Test
    fun `test find deliveries client valid list empty`(){

        every { deliveryRepository.findAllByIdClient(deliveryRequest.idClient) } returns listOf()
        assert( deliveryService.listAllDeliveryesClient(deliveryRequest.idClient).isEmpty() )

    }

    @Test
    fun `test find delivery client valid`(){

        every { deliveryRepository.findByIdAndIdClient(delivery.id, delivery.idClient) } returns Optional.of(delivery)
        assert( deliveryService.getDeliveryByClient(delivery.id, delivery.idClient).id == delivery.id )

    }

    @Test
    fun `test find delivery client valid empty throw`(){

        every { deliveryRepository.findByIdAndIdClient(delivery.id, delivery.idClient) } returns Optional.empty()
        assertThrows<EntityException> { deliveryService.getDeliveryByClient(delivery.id, delivery.idClient) }

    }
}