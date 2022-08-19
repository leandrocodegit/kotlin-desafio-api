package com.eduardo.kotlinAPI.controller

import com.eduardo.kotlinAPI.controller.request.BookRequest
import com.eduardo.kotlinAPI.service.BookService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/books")
class BookController(
    private val bookService: BookService
) {

    @ApiOperation(value = "Retrieve all books from the database")
    @GetMapping
    fun retrieveAllBooks() = ResponseEntity.ok(bookService.retrieveAllBooks())

    @ApiOperation(value = "Activate or deactivate a specific book")
    @PatchMapping("/activate/{id}")
    fun activateBook(@ApiParam("Id to locate the book and change it status") @PathVariable id: Int) = bookService.activateBook(id)

    @ApiOperation(value = "Insert a new book in the database")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    fun insertBook(@ApiParam(value = "Book to be inserted", required = true) @RequestBody request: BookRequest) {
        bookService.insertBook(request)
    }
}