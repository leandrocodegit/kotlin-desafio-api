package com.eduardo.kotlinAPI.component

import com.eduardo.kotlinAPI.controller.request.BookRequest
import com.eduardo.kotlinAPI.entity.Book
import com.eduardo.kotlinAPI.enums.BookStatus
import com.eduardo.kotlinAPI.util.URL_PRICE_BOOK
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo
import org.springframework.cloud.contract.spec.internal.HttpStatus.BAD_REQUEST
import org.springframework.cloud.contract.spec.internal.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import java.util.*

class BookComponent {

    companion object {
        fun createMockBookPriceRequest(bookName: String?, price: Int) {
            stubFor(
                get(urlPathEqualTo("/api/v1/price"))
                    .withQueryParam("bookName", equalTo(bookName))
                    .willReturn(
                        aResponse()
                            .withBody("200")
                            .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                    )
            )
        }
        fun createActiveBookEntity(
            id: Int = Random().nextInt(1000),
            name: String = "Livro ${Random().nextInt(1000)}",
            author: String = "Autor do Livro ${Random().nextInt(1000)}",
            publishYear: Int = Random().nextInt(1000)
        ): Book {
            return Book(id, name, author, publishYear, BookStatus.ATIVO, 1000)
        }

        fun createInactiveBookEntity(
            id: Int = Random().nextInt(1000),
            name: String = "Livro ${Random().nextInt(1000)}",
            author: String = "Autor do Livro ${Random().nextInt(1000)}",
            publishYear: Int = Random().nextInt(1000)
        ): Book {
            return Book(id, name, author, publishYear, BookStatus.INATIVO, 1000)
        }

        fun createBookRequest(
            name: String = "Livro ${Random().nextInt(1000)}",
            author: String = "Autor do Livro ${Random().nextInt(1000)}",
            publishYear: Int = Random().nextInt(1000)
        ): BookRequest {
            return BookRequest(name, author, publishYear)
        }
    }
}