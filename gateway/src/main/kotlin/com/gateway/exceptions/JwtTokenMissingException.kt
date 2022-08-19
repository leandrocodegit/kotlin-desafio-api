package com.gateway.exceptions

class JwtTokenMissingException: javax.naming.AuthenticationException()  {

    private val serialVersionUID = 1L

    fun JwtTokenMissingException(msg: String?) {
    }
}