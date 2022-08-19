package com.cliente.api.repository

import com.cliente.api.entity.Usuario
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.Pageable
import java.util.*

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Long> {

    fun findByEmail(email: String): Optional<Usuario>
    override fun findAll(pageable: Pageable): Page<Usuario>
}