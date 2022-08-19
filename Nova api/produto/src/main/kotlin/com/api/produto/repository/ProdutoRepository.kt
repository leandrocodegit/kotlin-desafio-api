package com.api.produto.repository

import com.api.produto.model.Produto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutoRepository: JpaRepository<Produto, String>{

        override fun findAll(pageable: Pageable): Page<Produto>
}