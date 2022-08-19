package com.eduardo.kotlinAPI.controller

import com.eduardo.kotlinAPI.AbstractTest
import com.eduardo.kotlinAPI.component.BookComponent
import com.eduardo.kotlinAPI.entity.Book
import com.eduardo.kotlinAPI.repository.BookRepository
import com.eduardo.kotlinAPI.util.*
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.properties.Delegates

class BookControllerTest : AbstractTest() {

    @Autowired
    private lateinit var bookRepository: BookRepository

    private lateinit var activatedBook: Book
    private lateinit var deactivatedBook: Book
    private lateinit var bookList: List<Book>
    private var id by Delegates.notNull<Int>()

    @BeforeEach
    fun setup() {
        activatedBook = BookComponent.createActiveBookEntity()
        deactivatedBook = BookComponent.createInactiveBookEntity()
        bookList = listOf(activatedBook, deactivatedBook)

        bookRepository.saveAll(bookList)
        id = bookRepository.findAll()[0].id!!
        bookRepository.flush()
    }

    @AfterEach
    fun eraseDatabase() {
        bookRepository.deleteAll()
    }

    @Test
    fun `when retrieve all the book from the database`() {
        this.mockMvc.perform(
            get(URL_BOOKS)
                .contentType(APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].id").value(id))
    }

    @Test
    fun `when successfully call the insert book endpoint`() {
        val bookRequest = BookComponent.createBookRequest()
        val body = ObjectMapper().writeValueAsString(bookRequest)

        BookComponent.createMockBookPriceRequest(bookRequest.name, 5600)

        this.mockMvc.perform(
            post(URL_PRICE_BOOK)
                .contentType(APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isCreated)
    }

    @Test
    fun `when successfully change a book status to INATIVO`() {

        this.mockMvc.perform(
            patch("$URL_BOOKS$URL_BOOKS_CHANGE_STATUS", id)
                .contentType(APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(INATIVO))
    }

    @Test
    fun `when successfully change a book status to ATIVO`() {
        id = bookRepository.findAll()[1].id!!
        this.mockMvc.perform(
            patch("$URL_BOOKS$URL_BOOKS_CHANGE_STATUS", id)
                .contentType(APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value(ATIVO))
    }
}