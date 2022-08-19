package com.eduardo.kotlinAPI.controller.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

class BookRequest(

    @NotBlank
    @Size(min = 4)
    val name: String,
    @field:NotBlank
    @Size(min = 4)
    val author: String,
    @NotBlank
    @Size(min = 4)
    @Positive
    val publishYear: Int
)
