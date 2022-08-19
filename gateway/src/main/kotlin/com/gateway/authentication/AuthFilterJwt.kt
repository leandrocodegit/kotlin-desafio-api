package com.gateway.authentication

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange

@Component
class AuthFilterJwt(private var webClientBuilder: WebClient.Builder?): AbstractGatewayFilterFactory<AuthFilterJwt.Config>(Config::class.java) {

      override fun apply(config: Config?): GatewayFilter? {
          println("COMPONENT")
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->
            if (!exchange.request.headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                throw RuntimeException("Missing authorization information")
            }
            val authHeader = exchange.request.headers[HttpHeaders.AUTHORIZATION]!![0]
            val parts =
                authHeader.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            println("TOKEN ${parts[1]}")
            if (parts.size != 2 || "Bearer" != parts[0]) {
                throw RuntimeException("Incorrect authorization structure")
            }
            webClientBuilder!!.build()
                .post()
                .uri("http://localhost:8081/v1/auth/validateToken?token=" + parts[1])
                .retrieve().bodyToMono(UserDTO::class.java)
                .map { userDto ->
                    exchange.request
                        .mutate()
                        .header("X-auth-user-id",  userDto.id.toString())
                    exchange
                }.flatMap { exchange: ServerWebExchange? -> chain.filter(exchange) }
        }
    }

    class Config {
    companion object// empty class as I don't need any particular configuration
    }

}