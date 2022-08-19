package com.api.delivery.integracao.client.reponse

class ClientResponse(
    var id: Long,
    var email: String,
    var nome: String,
    var idade: Int,
    var enderecos: List<EnderecoResponse>,
    var telefone: String,
    var isActive: Boolean
)