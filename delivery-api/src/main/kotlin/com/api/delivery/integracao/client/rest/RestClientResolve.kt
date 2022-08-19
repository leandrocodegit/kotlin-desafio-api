package com.api.delivery.integracao.client.rest

import com.api.delivery.enuns.CodeError
import com.api.delivery.exceptions.EntityException
import com.api.delivery.integracao.client.reponse.ClientResponse
import feign.codec.DecodeException
import org.springframework.stereotype.Component

@Component
class RestClientResolve(private val restClient: RestClient): RestClient {

    override fun getClient(id: Long): ClientResponse {
        return try{
            restClient.getClient(id)
        } catch (ex: DecodeException){
            throw EntityException(ex.localizedMessage, CodeError.REST_ERROR)
        }

    }

    override fun getListAllClient(): ClientResponse {
        TODO("Not yet implemented")
    }
}