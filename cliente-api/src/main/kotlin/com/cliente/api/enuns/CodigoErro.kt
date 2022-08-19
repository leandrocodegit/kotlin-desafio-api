package com.cliente.api.enuns

enum class CodigoErro(var value: String) {

    NOT_FOUND("001"),
    DUPLICATE("002"),
    ACTIVE("003"),
    INACTIVE("004"),
    ENDERECO_NOT_FOUND("005"),
    ENDERECO_BLANK("006"),
    FORMAT_INVALID("007")
}