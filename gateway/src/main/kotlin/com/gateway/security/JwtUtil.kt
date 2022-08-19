package com.gateway.security

import com.gateway.exceptions.JwtTokenMalformedException
import com.gateway.exceptions.JwtTokenMissingException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class JwtUtil {
    @Value("\${token.jwt.secret}")
    private val jwtSecret: String? = null
    fun getClaims(token: String): Claims? {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token.replace("Bearer ","").trim()).body
        } catch (e: Exception) {
            println(e.message + " => " + e)
        }
        return null
    }

    fun validateToken(token: String): Boolean {
        try {
                Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token.replace("Bearer ","").trim())
            return true
        } catch (ex: Exception) {
            println(ex.message)
        }
        return false
    }


}