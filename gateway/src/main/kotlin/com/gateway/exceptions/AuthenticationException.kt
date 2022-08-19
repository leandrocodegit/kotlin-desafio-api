package com.gateway.exceptions

class AuthenticationException(message: String, codeError: String): RuntimeException(message) {
}