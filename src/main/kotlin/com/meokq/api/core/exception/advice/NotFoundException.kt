package com.meokq.api.core.exception.advice

class NotFoundException : Exception {
    constructor() : super()
    constructor(message: String = "There is no data that meets the conditions.") : super(message)
    constructor(message: String = "There is no data that meets the conditions.", cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}