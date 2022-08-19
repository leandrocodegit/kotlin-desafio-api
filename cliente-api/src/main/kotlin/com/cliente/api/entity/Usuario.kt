package com.cliente.api.entity

import com.cliente.api.enuns.StatusUsuario
import org.jetbrains.annotations.NotNull
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var email: String,
    var nome: String,
    @Size(min = 1, max = 100)
    var idade: Int,
    @OneToMany(cascade = arrayOf(CascadeType.ALL))
    var enderecos: List<Endereco>?,
    var telefone: String,
    var senha: String,
    var isActive: Boolean)    {
}

