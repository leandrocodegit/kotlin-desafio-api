package com.gateway.authentication

class UserDTO(
    val id: Long,
    private val login: String,
    private val token: String
)