package com.api.delivery.controller

import com.api.delivery.build.Builder
import com.api.delivery.constantes.URL_DELIVERIES
import com.api.delivery.constantes.URL_DELIVERIES_ID_CLIENT
import com.api.delivery.constantes.URL_DELIVERIES_ID_CLIENT_DELIVERY
import com.api.delivery.consumer.listner.RabbitMQListener
import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.controller.request.ProductRequest
import com.api.delivery.integracao.client.reponse.ClientResponse
import com.api.delivery.integracao.client.rest.RestClient
import com.api.delivery.model.Delivery
import com.api.delivery.repository.DeliveryRepository
import com.api.delivery.service.DeliveryService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@ExtendWith(MockKExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class DeliveryControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var deliveryService: DeliveryService
    @MockkBean
    private lateinit var deliveryRepository: DeliveryRepository
    @MockkBean
    private lateinit var restClient: RestClient
    @MockkBean
    private lateinit var rabbitMQListener: RabbitMQListener

    private lateinit var delivery: Delivery
    private lateinit var deliveryRequest: DeliveryRequest
    private lateinit var clientResponse: ClientResponse

    @BeforeEach
    fun setup(){

        delivery = Builder.createDelivery()
        deliveryRequest = Builder.createDeliveryRequest()
        clientResponse = Builder.createClientResponseActive()

        every { deliveryRepository.save(any()) } returns delivery
        every { deliveryRepository.findByIdAndIdClient(delivery.id, delivery.idClient) } returns Optional.of(delivery)
        every { deliveryRepository.findAllByIdClient(delivery.idClient) } returns listOf(Builder.createDelivery(), Builder.createDelivery())
        every { restClient.getClient(delivery.idClient) } returns clientResponse
        every { rabbitMQListener.receiveUserModelMessage(any()) }
    }

    @Test
    fun `test create new delivery valid`(){

        val body = ObjectMapper().writeValueAsString(deliveryRequest)

        this.mockMvc.perform(
            post(URL_DELIVERIES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isOk)
    }

    @Test
    fun `test create new delivery invalid id client`(){

        deliveryRequest.idClient = -0
        val body = ObjectMapper().writeValueAsString(deliveryRequest)

        this.mockMvc.perform(
            post(URL_DELIVERIES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test create new delivery invalid products empty`(){

        deliveryRequest.products = listOf()
        val body = ObjectMapper().writeValueAsString(deliveryRequest)

        this.mockMvc.perform(
            post(URL_DELIVERIES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test create new delivery invalid products qtd invalid`(){

        deliveryRequest.products = listOf(ProductRequest(1L,0))
        val body = ObjectMapper().writeValueAsString(deliveryRequest)

        this.mockMvc.perform(
            post(URL_DELIVERIES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test create new delivery invalid products id invalid`(){

        deliveryRequest.products = listOf(ProductRequest(0,10))
        val body = ObjectMapper().writeValueAsString(deliveryRequest)

        this.mockMvc.perform(
            post(URL_DELIVERIES)
            .contentType(MediaType.APPLICATION_JSON)
            .content(body))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test find all delivery by id client valid`(){

        var list: List<Delivery> = deliveryRepository.findAllByIdClient(delivery.idClient)

        this.mockMvc.perform(
            get(URL_DELIVERIES_ID_CLIENT, delivery.idClient)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].idClient").value(list.first().idClient))
    }

    @Test
    fun `test find all delivery by id client invalid`(){

        every { deliveryRepository.findAllByIdClient(0) } returns listOfNotNull()

        this.mockMvc.perform(
            get(URL_DELIVERIES_ID_CLIENT, 0)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isEmpty)
    }

    @Test
    fun `test find delivery by id client and id delivery valid`(){

        var findDelivery = deliveryRepository.findByIdAndIdClient(delivery.id,delivery.idClient).get()

        this.mockMvc.perform(
            get(URL_DELIVERIES_ID_CLIENT_DELIVERY, delivery.idClient, delivery.id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(findDelivery.id.toString()))
    }

    @Test
    fun `test find delivery by id client and id delivery invalid`(){

        every { deliveryRepository.findByIdAndIdClient(delivery.id, 0) } returns Optional.empty()

        this.mockMvc.perform(
            get(URL_DELIVERIES_ID_CLIENT_DELIVERY, delivery.id, 0)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `test find delivery by id client invalid and id delivery invalid`(){

        this.mockMvc.perform(
            get(URL_DELIVERIES_ID_CLIENT_DELIVERY, 0,  0)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.containsError").value(true))
    }
}