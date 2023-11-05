package com.meokq.api.core.exception.advice

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.response.BaseResponse
import jakarta.validation.ValidationException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class ExControllerAdvice {

    /**
     * 파라미터 형식 오류
     */
    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        ValidationException::class)
    fun invalidParameter(e : Exception): ResponseEntity<BaseResponse> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResponse(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }
}