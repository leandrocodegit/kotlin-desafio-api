package com.api.delivery.integracao.client.rest

import com.api.delivery.integracao.client.reponse.ClientResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "restClient",
    url = "\${integration.client.url}"
)
interface RestClient {

    @GetMapping("/api/v1/users/{id}")
    fun getClient(@PathVariable id: Long): ClientResponse
    @GetMapping("/api/v1/users")
    fun getListAllClient(): ClientResponse
}