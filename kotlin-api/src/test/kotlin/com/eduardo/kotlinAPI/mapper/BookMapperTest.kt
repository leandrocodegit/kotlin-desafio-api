package com.eduardo.kotlinAPI.mapper

import com.eduardo.kotlinAPI.component.BookComponent
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mapstruct.factory.Mappers

class BookMapperTest {

    private val bookMapper = Mappers.getMapper(BookMapper::class.java)

    @Test
    fun `when the mapper create an entity from a request`() {
        val bookRequest = BookComponent.createBookRequest()
        val book = bookMapper.toEntity(bookRequest)

        assertEquals(book.name, bookRequest.name)
        assertEquals(book.author, bookRequest.author)
        assertEquals(book.publishYear, bookRequest.publishYear)
    }
}