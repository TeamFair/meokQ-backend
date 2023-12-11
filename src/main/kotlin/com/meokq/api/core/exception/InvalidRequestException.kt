package com.meokq.api.core.exception

class InvalidRequestException : Exception {
    constructor() : super()
    constructor(message: String = "Invalid request.") : super(message)
    constructor(message: String = "Invalid request.", cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}