package com.gateway

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GatwayApplication

fun main(args: Array<String>) {
	runApplication<GatwayApplication>(*args)
}


