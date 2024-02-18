package com.meokq.api.core.exception

import io.jsonwebtoken.JwtException

class TokenException : JwtException {
    constructor(message: String = "Invalid token.") : super(message)
    constructor(message: String = "Invalid token.", cause: Throwable) : super(message, cause)
}