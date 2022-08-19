package com.gateway.routes

import com.gateway.security.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerRouteConfig{

    @Autowired
    private lateinit var filter: JwtAuthenticationFilter

    @Bean
    fun routesPurchasesSwagger(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "purchases-swagger"
        ) { r: PredicateSpec ->
            r.path("/docs/compras/v2/api-docs").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                ).rewritePath("/docs/compras/v2/api-docs","/v2/api-docs")
            }.uri("http://localhost:9091/")
        }.route(
                "purchases-swagger"
            ) { r: PredicateSpec ->
                r.path("/docs/compras/**").filters { f: GatewayFilterSpec ->
                    f.filter(
                        filter
                    ).rewritePath("/docs/compras","/")
                }.uri("http://localhost:9091/")
            }.build()
    }

    @Bean
    fun routesSwaggerProducts(builder: RouteLocatorBuilder): RouteLocator? {
        return builder.routes().route(
            "products-swagger-api-docs"
        ) { r: PredicateSpec ->
            r.path("/docs/produtos/v2/api-docs").filters { f: GatewayFilterSpec ->
                f.filter(
                    filter
                ).rewritePath("/docs/produtos/v2/api-docs","/v2/api-docs")
            }.uri("http://localhost:9090/")
        }.route(
                "products-swagger-ui"
            ) { r: PredicateSpec ->
                r.path("/docs/produtos/**").filters { f: GatewayFilterSpec ->
                    f.filter(
                        filter
                    ).rewritePath("/docs/produtos","/")
                }.uri("http://localhost:9090/")
            }.build()
    }
}