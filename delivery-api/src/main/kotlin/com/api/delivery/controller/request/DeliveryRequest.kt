package com.api.delivery.controller.request

import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

class DeliveryRequest(
    @field:Min(1)
    var idClient: Long,
    @field:Valid
    @field:NotEmpty
    var products: List<ProductRequest>
)

