package com.cliente.api.entity

import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Endereco(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @NotBlank
    var rua: String,
    @NotBlank
    var numero: String?,
    @NotBlank
    var cidade: String,
    @NotBlank
    var estado: String,
    @NotBlank
    var cep: String,
    var isDelivery: Boolean)