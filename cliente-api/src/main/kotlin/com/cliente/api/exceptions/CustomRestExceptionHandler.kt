package com.cliente.api.exceptions

import com.cliente.api.enuns.CodigoErro
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.UnexpectedTypeException

@ControllerAdvice
class CustomRestExceptionHandler(): ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityResponseException::class)
    fun handlerEntityNotFoundException(ex: EntityResponseException): ResponseEntity<ResponseErro> {
        return ResponseEntity<ResponseErro>(
            ResponseErro(ex.message, ex.codigo.name, ex.codigo.value,listOf(ex.localizedMessage)),
            HttpHeaders(),
            HttpStatus.ACCEPTED);
    }
    @ExceptionHandler(UnexpectedTypeException::class)
    fun handlerValidadeException(ex: UnexpectedTypeException): ResponseEntity<ResponseErro> {
        return ResponseEntity<ResponseErro>(
            ResponseErro(
                "Formato invalido",
                CodigoErro.FORMAT_INVALID.name,
                CodigoErro.FORMAT_INVALID.value,
                listOf(ex.localizedMessage)
            ),
            HttpHeaders(),
            HttpStatus.BAD_REQUEST);
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(ResponseErro(
            "Formato invalido",
            CodigoErro.FORMAT_INVALID.name,
            CodigoErro.FORMAT_INVALID.value,
            listOf(ex.localizedMessage)
        ))
    }
}