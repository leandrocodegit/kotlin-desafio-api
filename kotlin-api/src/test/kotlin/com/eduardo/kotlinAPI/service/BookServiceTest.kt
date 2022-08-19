package com.eduardo.kotlinAPI.service

import com.eduardo.kotlinAPI.component.BookComponent
import com.eduardo.kotlinAPI.controller.request.BookRequest
import com.eduardo.kotlinAPI.entity.Book
import com.eduardo.kotlinAPI.enums.BookStatus
import com.eduardo.kotlinAPI.enums.BookStatus.ATIVO
import com.eduardo.kotlinAPI.enums.BookStatus.INATIVO
import com.eduardo.kotlinAPI.integration.feign.client.PriceClient
import com.eduardo.kotlinAPI.mapper.BookMapper
import com.eduardo.kotlinAPI.repository.BookRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.only
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    @InjectMocks
    private lateinit var bookService: BookService
    @Mock
    private lateinit var bookRepository: BookRepository
    @Mock
    private lateinit var bookMapper: BookMapper
    @Mock
    private lateinit var priceClient: PriceClient

    private lateinit var book: Book
    private lateinit var bookRequest: BookRequest
    private lateinit var bookList: List<Book>

    @BeforeEach
    fun setup() {
        bookList = listOf(
            BookComponent.createActiveBookEntity(),
            BookComponent.createActiveBookEntity(),
            BookComponent.createInactiveBookEntity(),
            BookComponent.createActiveBookEntity(),
            BookComponent.createActiveBookEntity(),
            BookComponent.createInactiveBookEntity()
            )
    }

    @Test
    fun `when get all books from repository should return a populated list`() {
        val lista: List<Book>?

        `when`(bookRepository.findAll()).thenReturn(bookList)

        lista = bookService.retrieveAllBooks()
        verify(bookRepository, only()).findAll()

        assertEquals(bookList, lista)
        assertEquals(bookList.size, lista.size)
    }

    @Test
    fun `when successfully insert a book in the repository`() {
        book = BookComponent.createActiveBookEntity()
        bookRequest = BookComponent.createBookRequest()

        `when`(bookMapper.toEntity(bookRequest)).thenReturn(book)
        `when`(bookRepository.save(book)).thenReturn(book)
        `when`(priceClient.retrievePrice(book.name)).thenReturn(book.price)
        bookService.insertBook(bookRequest)
        verify(bookMapper, only()).toEntity(bookRequest)
        verify(bookRepository, only()).save(book)
    }

    @Test
    fun `when deactivate a book status`() {
        book = BookComponent.createActiveBookEntity()
        val statusAnterior = book.status
        val statusPosterior: BookStatus
        val id: Int = book.id!!

        `when`(bookRepository.findById(id)).thenReturn(Optional.of(book))
        `when`(bookRepository.save(book)).thenReturn(book)
        statusPosterior =  bookService.activateBook(id).status

        verify(bookRepository, atLeastOnce()).findById(id)
        verify(bookRepository, atLeastOnce()).save(book)

        assertEquals(ATIVO, statusAnterior)
        assertEquals(INATIVO, statusPosterior)
        assertNotEquals(statusPosterior, statusAnterior)
    }

    @Test
    fun `when activate a book status`() {
        book = BookComponent.createInactiveBookEntity()
        val statusAnterior = book.status
        val statusPosterior: BookStatus
        val id: Int = book.id!!

        `when`(bookRepository.findById(id)).thenReturn(Optional.of(book))
        `when`(bookRepository.save(book)).thenReturn(book)
        statusPosterior =  bookService.activateBook(id).status

        verify(bookRepository, atLeastOnce()).findById(id)
        verify(bookRepository, atLeastOnce()).save(book)

        assertEquals(ATIVO, statusPosterior)
        assertEquals(INATIVO, statusAnterior)
        assertNotEquals(statusPosterior, statusAnterior)
    }
}