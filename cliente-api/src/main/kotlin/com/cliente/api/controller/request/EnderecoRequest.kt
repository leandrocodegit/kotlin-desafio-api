package com.cliente.api.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty

class EnderecoRequest(
        @field:NotEmpty
        var rua: String,
        @field:NotEmpty
        var numero: String
)