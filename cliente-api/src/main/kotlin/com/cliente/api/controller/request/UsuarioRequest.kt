package com.cliente.api.controller.request


import javax.validation.constraints.Email
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

class UsuarioRequest(
    @field:Email
    var email: String,
    @field:NotBlank
    var nome: String,
    @field:NotNull
    @field:Min(18)
    @field:Max(120)
    var idade: Int,
    var endereco: EnderecoRequest,
    @field:NotBlank
    var telefone: String,
    var senha: String,
    )