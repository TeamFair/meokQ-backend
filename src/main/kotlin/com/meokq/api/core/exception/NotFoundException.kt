package com.meokq.api.core.exception

class NotFoundException : Exception {
    constructor() : super()
    constructor(message: String = "There is no data that meets the conditions.") : super(message)
    constructor(message: String = "There is no data that meets the conditions.", cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}