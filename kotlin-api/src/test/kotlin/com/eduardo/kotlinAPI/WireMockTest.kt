package com.eduardo.kotlinAPI

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType.APPLICATION_JSON

class WireMockTest : AbstractTest() {

    private val wireMockServer = WireMockServer()

    @Test
    fun `testing wiremock`() {
        wireMockServer.stubFor(get(urlPathMatching("/api/v1/price"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", APPLICATION_JSON.toString())
                .withBody(20.00.toString())
            )
        )
    }
}