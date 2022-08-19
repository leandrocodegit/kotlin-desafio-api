package com.api.delivery.service

import com.api.delivery.enuns.CodeError
import com.api.delivery.exceptions.EntityException
import com.api.delivery.integracao.client.reponse.ClientResponse
import com.api.delivery.integracao.client.reponse.EnderecoResponse
import com.api.delivery.integracao.client.rest.RestClient
import com.api.delivery.model.Delivery
import com.api.delivery.repository.DeliveryRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

@Service
class DeliveryService(
    private val deliveryRepository: DeliveryRepository,
    private val restClient: RestClient
) {

    fun saveDelivery(delivery: Delivery): Delivery{
        var clientResponse: ClientResponse = restClient.getClient(delivery.idClient)
        if(clientResponse.isActive.not()) throw EntityException("Cliente nao esta ativo", CodeError.ACTIVE_RESPONSE)
        var listEndereco: List<EnderecoResponse> = clientResponse.enderecos.filter { e -> e.isDelivery}
        if(listEndereco.isEmpty()) throw EntityException("Cliente nao possui um endereco de entrega", CodeError.ADDRESS_NOT_FOUND)
        delivery.addressDelivery = listEndereco.first().toString()
      return deliveryRepository.save(delivery)
    }

    fun listAllDeliveryesClient(id: Long): List<Delivery>{
        return deliveryRepository.findAllByIdClient(id)
    }

    fun getDeliveryByClient(id: ObjectId, idClient: Long): Delivery {
        return deliveryRepository.findByIdAndIdClient(id, idClient).orElseThrow {
            throw EntityException("Id delivery nao encotrado", CodeError.NOT_FOUND)
        }
    }

}