package com.cliente.api.util

import com.cliente.api.controller.request.EnderecoRequest
import com.cliente.api.controller.response.EnderecoResponse
import com.cliente.api.entity.Endereco
import com.cliente.api.enuns.CodigoErro
import com.cliente.api.exceptions.EntityResponseException
import java.util.Random

class BuscaEndereco {

    val enderecos:List<Endereco> = listOf(
        Endereco(0,"Rua um", "","Um","SP","",true),
        Endereco(0,"Rua dois", "","Dois","SP","",true),
        Endereco(0,"Rua tres", "","Tres","SP","",true),
        Endereco(0,"Rua quatro", "","Quatro","SP","",true),
        Endereco(0,"Rua cinco", "","Cinco","SP","",true)
    )

    fun findEndereco(enderecoRequest: EnderecoRequest): Endereco{
        if(enderecoRequest.rua.isBlank()) throw EntityResponseException("Campo rua invalido", CodigoErro.ENDERECO_BLANK)
        var endereco:Endereco? = enderecos.find { endereco: Endereco ->  endereco.rua.lowercase().contains(enderecoRequest.rua.lowercase())}
        if(endereco == null) throw EntityResponseException("Endereco nao encontrado", CodigoErro.ENDERECO_NOT_FOUND)
        var cepRandom: Int = 0
        while(cepRandom.toString().length < 8){
            cepRandom = Random().nextInt(99999999)
        }
        return Endereco(endereco.id, endereco.rua, enderecoRequest.numero, endereco.cidade, endereco.estado,cepRandom.toString(),true)
    }
}