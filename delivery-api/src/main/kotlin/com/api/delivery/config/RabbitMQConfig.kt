package com.api.delivery.config


import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {


    @Bean
    fun createUserQueue() : Queue?{
        print("")
        return Queue("PURCHASES-MODEL-QUEUE", true)
    }


}