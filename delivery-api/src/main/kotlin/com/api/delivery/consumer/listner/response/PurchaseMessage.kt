package com.api.purchases.controller.response

class PurchasesResponse(
    var id: String,
    var idClient: Long,
    var product: ProductResponse,
    var total: Double,
    var addressDelivery: String){

    override fun toString(): String {
        return "PurchasesResponse(id=$id, idClient=$idClient, product=$product, total=$total, addressDelivery=$addressDelivery)"
    }
}

class ProductResponse(
    var idProduct: Long,
    var name: String?,
    var qtd: Int,
    var valor: Double)

