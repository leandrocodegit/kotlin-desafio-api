package com.gateway.routes

import com.gateway.authentication.AuthFilterJwt
import com.gateway.security.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GatewayRouteConfig {

    @Autowired
    private lateinit var filter: JwtAuthenticationFilter

    @Bean
    fun routesAuth(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "auth"
        ) { r: PredicateSpec ->
            r.path("/auth/login").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                )
            }.uri("http://localhost:8081/")
        }
            .route(
                "webjars"
            ) { r: PredicateSpec ->
                r.path("/webjars/**").filters { f: GatewayFilterSpec ->
                    f.filter(
                        filter
                    )
                }.uri("http://localhost:9091/")
            }.build()
    }

    @Bean
    fun routesProducts(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes()
            .route(
                "produtos-api"
            ) { r: PredicateSpec ->
                r.path("/produtos/**").filters { f: GatewayFilterSpec ->
                    f.filter(
                        filter
                    ).rewritePath("/produtos", "/api/v1/products")
                }.uri("http://localhost:9090/")
            }.build()
    }

    @Bean
    fun routesPurchases(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "purchases-api"
        ) { r: PredicateSpec ->
            r.path("/compras/**").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                ).rewritePath("/compras", "/api/v1/purchases")
            }.uri("http://localhost:9091/")
        }.build()
    }

    @Bean
    fun routesUsers(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "users-api"
        ) { r: PredicateSpec ->
            r.path("/clientes/**").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                ).rewritePath("/clientes", "/api/v1/users")
            }.uri("http://localhost:9092/")
        }.build()
    }

    @Bean
    fun routesDelivery(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "delivery-api"
        ) { r: PredicateSpec ->
            r.path("/entregas/**").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                ).rewritePath("/entregas", "/api/v1/delivery")
            }.uri("http://localhost:9093/")
        }.build()
    }
}