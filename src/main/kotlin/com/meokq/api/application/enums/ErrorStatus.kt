package com.meokq.api.application.enums

import org.springframework.http.HttpStatus

enum class ErrorStatus(
    val status : HttpStatus, val message : String
) {
    OK(HttpStatus.OK, "Your request has been processed successfully."),
    CREATED(HttpStatus.CREATED, "The resource was created successfully."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "You do not have permission to access the resource."),
    NOT_FOUND_DATA(HttpStatus.NOT_ACCEPTABLE, "There is no data matching the conditions."),
    REQUEST_TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "It took too much time to process your request."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "The parameter format is incorrect."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred.")
}