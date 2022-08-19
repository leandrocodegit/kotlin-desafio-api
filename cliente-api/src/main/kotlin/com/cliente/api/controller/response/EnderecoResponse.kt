package com.cliente.api.controller.response

class EnderecoResponse(
        var id: Long,
        var rua: String,
        var numero: String,
        var cidade: String,
        var estado: String,
        var isDelivery: Boolean
)