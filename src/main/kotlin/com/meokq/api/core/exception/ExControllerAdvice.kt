package com.meokq.api.core.exception

import com.meokq.api.application.enums.ErrorStatus
import com.meokq.api.core.dto.BaseResp
import jakarta.validation.ValidationException
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.util.unit.DataSize
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@Slf4j
@RestControllerAdvice
class ExControllerAdvice(
    @Value("\${multipart.max-upload-size}") private val maxUploadSize: DataSize,
) {

    /**
     * 파라미터 형식 오류
     */
    @ExceptionHandler(
        HttpMessageNotReadableException::class,
        ValidationException::class,
        MethodArgumentNotValidException::class)
    fun invalidParameter(e : Exception): ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResp(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 파라미터 형식 오류
     */
    @ExceptionHandler(
        NotUniqueException::class)
    fun `Unique-index-or-primary-key-violation`(e : Exception): ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResp(null, errorStatus, "Unique index or primary key violation.")
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 데이터가 없을 때 오류
     */
    @ExceptionHandler(NotFoundException::class)
    fun notFound(e : NotFoundException): ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.NOT_FOUND_DATA
        val errorResponse = BaseResp(
            data = null,
            errorStatus = errorStatus,
            errMessage = e.message
        )
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 업로드 할 수 있는 파일 크기 오류
     */
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun maxUploadSizeExceeded(e : Exception) : ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.BAD_REQUEST
        val errorResponse = BaseResp(null, errorStatus, "File size exceeds the limit of ${maxUploadSize.toKilobytes()} KB")
        return ResponseEntity(errorResponse, errorStatus.status)
    }

    /**
     * 기타오류
     */
    @ExceptionHandler(Exception::class)
    fun internalServerError(e : Exception): ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        val errorResponse = BaseResp(null, errorStatus)
        return ResponseEntity(errorResponse, errorStatus.status)
    }
}