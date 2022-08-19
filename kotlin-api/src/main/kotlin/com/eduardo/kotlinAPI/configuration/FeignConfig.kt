package com.eduardo.kotlinAPI.configuration

import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableFeignClients(basePackages = ["com.eduardo.kotlinAPI.integration.feign.client"])
class FeignConfig {

    @Bean
    fun errorDecoder(): FeignErrorDecoder? {
        return FeignErrorDecoder()
    }
}
