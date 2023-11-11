package com.meokq.api.core.exception

class NotUniqueException : Exception {
    constructor() : super()
    constructor(message: String = "Unique index or primary key violation.") : super(message)
    constructor(message: String = "Unique index or primary key violation.", cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}