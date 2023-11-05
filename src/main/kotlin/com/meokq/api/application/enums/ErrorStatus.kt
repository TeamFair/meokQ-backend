package com.meokq.api.application.enums

enum class ErrorStatus(
    val status : Int, val message : String
) {
    OK(200, "Your request has been processed successfully."),
    CREATED(201, "The resource was created successfully."),
    UNAUTHORIZED(401, "You do not have permission to access the resource."),
    NOT_FOUND_DATA(501, "There is no data matching the conditions."),
    REQUEST_TIMEOUT(408, "It took too much time to process your request."),
    BAD_REQUEST(400, "The parameter format is incorrect."),
    INTERNAL_SERVER_ERROR(500, "An unknown error occurred.")
}