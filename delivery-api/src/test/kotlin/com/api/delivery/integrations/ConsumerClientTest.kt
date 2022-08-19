package com.api.delivery.integrations

import com.api.delivery.consumer.listner.RabbitMQListener
import com.api.delivery.integracao.client.rest.RestClient
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus


class ConsumerClientTest: AbstractTest() {

    @MockkBean
    private lateinit var rabbitMQListener: RabbitMQListener
    @Autowired
    private lateinit var restClient: RestClient

    @Test
    fun `test find client active`(){

        DeliveryComponent.createMockBookPriceRequest(HttpStatus.FORBIDDEN)


        DeliveryComponent.createMockBookPriceRequest(HttpStatus.OK)

       var  client = restClient.getListAllClient()


        assert(client.id == 1L)

    }

    @Test
    fun `test find client not alread`(){



     //   assertThrows<DecodeException> {restClient.getClient(60)}

       // assertThat(client.isActive.not())
       // assert(client.id == 45L)

    }
}