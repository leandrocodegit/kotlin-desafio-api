package com.api.delivery.exceptions

import com.api.delivery.enuns.CodeError

class EntityException(message: String, var codeError: CodeError): CustomException(message)