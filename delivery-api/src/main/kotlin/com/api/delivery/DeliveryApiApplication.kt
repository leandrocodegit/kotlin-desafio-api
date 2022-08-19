package com.api.delivery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class DeliveryApiApplication

fun main(args: Array<String>) {
	runApplication<DeliveryApiApplication>(*args)
}
