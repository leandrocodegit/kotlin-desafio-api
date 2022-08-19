package com.cliente.api.exceptions

import com.cliente.api.enuns.CodigoErro

class EntityResponseException(message: String, var codigo: CodigoErro): EntityException(message) {
    private val serialVersionUID = 7485635423254641L
}