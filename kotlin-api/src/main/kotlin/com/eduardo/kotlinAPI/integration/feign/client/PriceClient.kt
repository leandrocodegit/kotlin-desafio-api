package com.eduardo.kotlinAPI.integration.feign.client

import com.eduardo.kotlinAPI.configuration.FeignConfig
import feign.Headers
import feign.Param
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(
    name = "BookPriceAPI",
    url = "\${integration.bookPrice.url}"
)
interface PriceClient {

    @GetMapping("/api/v1/price")
    fun retrievePrice(@RequestParam bookName: String): Int
}
