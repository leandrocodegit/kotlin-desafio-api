package com.eduardo.kotlinAPI.entity

import com.eduardo.kotlinAPI.enums.BookStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "TB_LIVRO")
data class Book(

    @Id
    @Column(name = "id_book")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "ds_name")
    var name: String,

    @Column(name = "ds_author")
    var author: String,

    @Column(name = "publish_year")
    var publishYear: Int,

    @Column(name = "status")
    var status: BookStatus,

    @Column(name = "price")
    var price: Int
)
