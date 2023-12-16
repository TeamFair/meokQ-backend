package com.meokq.api.core.exception

class TokenException : Exception {
    constructor() : super()
    constructor(message: String = "Invalid token.") : super(message)
    constructor(message: String = "Invalid token.", cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}