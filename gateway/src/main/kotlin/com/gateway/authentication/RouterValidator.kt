package com.gateway.authentication

import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import java.util.List
import java.util.function.Predicate


class RouterValidator {
    var isSecured: Predicate<ServerHttpRequest> = Predicate<ServerHttpRequest> { request ->
        openApiEndpoints
            .stream()
            .noneMatch { uri: String ->
                request.uri.path.contains(uri)
            }
    }

    companion object {
        val openApiEndpoints =
            listOf("/auth/login",
                "/auth/register",
                "/docs/compras",
                "/docs/compras/csrf",
                "/docs/compras/swagger-ui.html",
                "/docs/compras/v2/api-docs",
                "/docs/compras/webjars/springfox-swagger-ui/",
                "/docs/compras/swagger-resources",
                "/docs/compras/swagger-resources/configuration/security",
                "/docs/compras/swagger-ui.html/swagger-resources/configuration/ui",
                "/docs/compras/swagger-resources/configuration/ui",
                "/docs/produtos",
                "/docs/produtos/swagger-ui.html",
                "/docs/produtos/v2/api-docs",
                "/docs/produtos/webjars/springfox-swagger-ui/",
                "/docs/produtos/swagger-resources",
                "/docs/produtos/swagger-resources/configuration/security",
                "/docs/produtos/swagger-ui.html/swagger-resources/configuration/ui",
                "/docs/produtos/swagger-resources/configuration/ui")
    }
}