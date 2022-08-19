package com.eduardo.kotlinAPI.configuration

import feign.Response
import feign.codec.ErrorDecoder

class FeignErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        return Exception(response!!.reason())
    }
}