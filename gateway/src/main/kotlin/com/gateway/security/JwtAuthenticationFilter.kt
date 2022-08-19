package com.gateway.security

import com.gateway.authentication.RouterValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.http.HttpStatus
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.function.Predicate

@Component
class JwtAuthenticationFilter : GatewayFilter {
    @Autowired
    private lateinit var jwtUtil: JwtUtil
    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val request = exchange.request
        val apiEndpoints = RouterValidator.openApiEndpoints
        val isApiSecured =
            Predicate { r: ServerHttpRequest ->
                apiEndpoints.stream()
                    .noneMatch { uri: String? ->
                        r.uri.path.contains(uri!!)
                    }
            }

        if (isApiSecured.test(request)) {
            if (!request.headers.containsKey("Authorization")) {
                val response = exchange.response
                response.statusCode = HttpStatus.UNAUTHORIZED
                return response.setComplete()
            }
            val token = request.headers.getOrEmpty("Authorization")[0]
            if(jwtUtil.validateToken(token).not()){
                val response = exchange.response
                response.statusCode = HttpStatus.FORBIDDEN
                return response.setComplete()
            }
            val claims = jwtUtil.getClaims(token)
             exchange.request.mutate().header("id", claims!!["id"].toString()).build()
        }
        return chain.filter(exchange)
    }
}
