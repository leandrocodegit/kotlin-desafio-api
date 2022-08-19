package com.api.delivery.build

import com.api.delivery.controller.request.DeliveryRequest
import com.api.delivery.controller.request.ProductRequest
import com.api.delivery.enuns.StatusDelivery
import com.api.delivery.integracao.client.reponse.ClientResponse
import com.api.delivery.integracao.client.reponse.EnderecoResponse
import com.api.delivery.model.Delivery
import com.api.delivery.model.Product
import org.bson.types.ObjectId

class Builder{

    companion object {

        var id: ObjectId = ObjectId.get()
        var idClient: Long = 1L
        var status: StatusDelivery = StatusDelivery.OPEN
        var products: List<Product> = listOf(Product(1L,10),Product(1L,10))
        var addressDelivery: String? = "Endereco de entrega"
        fun createDelivery(): Delivery{
            return Delivery(id, idClient, status, products, addressDelivery)
        }
        fun createDeliveryRequest(): DeliveryRequest{
            return DeliveryRequest(idClient, listOf(ProductRequest(1L, 10),ProductRequest(2L, 10)))
        }

        fun createClientResponseActive(): ClientResponse{
            return ClientResponse(
                idClient,
                "test@test.com",
                "Fulano test",
                33,
                listOf<EnderecoResponse>(
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", true),
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", false)),
                "11111",
                true)
        }
        fun createClientResponseActiveAddressEmpy(): ClientResponse{
            return ClientResponse(
                idClient,
                "test@test.com",
                "Fulano test",
                33,
                listOf<EnderecoResponse>(),
                "11111",
                true)
        }
        fun createClientResponseActiveAddressNotDelivery(): ClientResponse{
            return ClientResponse(
                idClient,
                "test@test.com",
                "Fulano test",
                33,
                listOf<EnderecoResponse>(
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", false),
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", false)),
                "11111",
                true)
        }
        fun createClientResponseInactive(): ClientResponse{
            return ClientResponse(
                idClient,
                "test@test.com",
                "Fulano test",
                33,
                listOf<EnderecoResponse>(
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", true),
                    EnderecoResponse(1L, "Rua teste", "zero", "cidade", "sp", true)),
                "11111",
                false)
        }
    }
}