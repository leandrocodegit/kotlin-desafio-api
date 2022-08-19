package com.cliente.api.controller.response

import com.cliente.api.enuns.StatusUsuario

 class UsuarioResponse(
    var id: Long,
    var email: String,
    var nome: String,
    var idade: Int,
    var enderecos: List<EnderecoResponse>,
    var telefone: String,
    var isActive: Boolean
    )