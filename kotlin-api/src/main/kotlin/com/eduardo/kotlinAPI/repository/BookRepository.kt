package com.eduardo.kotlinAPI.repository

import com.eduardo.kotlinAPI.entity.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Int>