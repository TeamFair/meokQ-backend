package com.meokq.api.core.exception

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.application.response.BaseResponse
import jakarta.validation.ValidationException
import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
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
        ValidationException::class,
        MethodArgumentNotValidException::class)
    fun invalidParameter(e : Exception): ResponseEntity<BaseResponse> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResponse(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 파라미터 형식 오류
     */
    @ExceptionHandler(
        NotUniqueException::class)
    fun `Unique-index-or-primary-key-violation`(e : Exception): ResponseEntity<BaseResponse> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResponse(null, errorStatus, "Unique index or primary key violation.")
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 데이터가 없을 때 오류
     */
    @ExceptionHandler(NotFoundException::class)
    fun notFound(e : Exception): ResponseEntity<BaseResponse> {
        val errorStatus = ErrorStatus.NOT_FOUND_DATA
        val errorResponse = BaseResponse(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 기타오류
     */
    @ExceptionHandler(Exception::class)
    fun internalServerError(e : Exception): ResponseEntity<BaseResponse> {
        val errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        val errorResponse = BaseResponse(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }
}