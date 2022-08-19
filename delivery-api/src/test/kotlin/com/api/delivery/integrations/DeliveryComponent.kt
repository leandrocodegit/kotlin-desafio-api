package com.api.delivery.integrations

import com.api.delivery.build.Builder
import com.github.tomakehurst.wiremock.client.WireMock.*
import com.google.gson.Gson
import org.springframework.cloud.contract.spec.internal.HttpStatus.OK
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class DeliveryComponent{



    companion object: AbstractTest(){

        fun createMockBookPriceRequest(status: HttpStatus) {
            stubFor(
                get(urlPathEqualTo("/api/v1/users"))
                    .willReturn(
                        aResponse()
                            .withStatus(status.value())
                            .withBody(Gson().toJson(Builder.createClientResponseActive()))
                            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    )
            )
        }

        fun createClientStub(id: Long){
            stubFor(
                get(urlPathEqualTo("/api/v1/users"))
                    .willReturn(
                        aResponse()
                            .withStatus(OK)
                            .withHeader("Context-type", MediaType.APPLICATION_JSON_VALUE)
                            .withBody(Gson().toJson(Builder.createClientResponseActive()))
                    )
            )
        }

    }
}