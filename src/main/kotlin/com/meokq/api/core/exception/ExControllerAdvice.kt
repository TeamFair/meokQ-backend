package com.meokq.api.core.exception

import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.enums.ErrorStatus
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
        return handleError(e.message, ErrorStatus.BAD_REQUEST)
    }

    /**
     * 데이터가 없을 때 오류
     */
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundData(e : NotFoundException): ResponseEntity<BaseResp> {
        return handleError(e.message, ErrorStatus.NOT_FOUND_DATA)
    }

    /**
     * 업로드 할 수 있는 파일 크기 오류
     */
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handlePayloadTooLarge(e : Exception) : ResponseEntity<BaseResp> {
        return handleError(
            "File size exceeds the limit of ${maxUploadSize.toKilobytes()} KB",
            ErrorStatus.PAYLOAD_TOO_LARGE)
    }

    /**
     * 유효하지 않은 요청 오류
     */
    @ExceptionHandler(
        InvalidRequestException::class,
        NotUniqueException::class
    )
    fun handleBadRequest(e : Exception) : ResponseEntity<BaseResp> {
        return handleError(e.message, ErrorStatus.BAD_REQUEST)
    }

    /**
     * token 관련 오류
     */
    @ExceptionHandler(TokenException::class)
    fun handleUnauthorized(e : Exception) : ResponseEntity<BaseResp> {
        return handleError(e.message, ErrorStatus.UNAUTHORIZED)
    }

    /**
     * 기타오류
     */
    @ExceptionHandler(Exception::class)
    fun handleInternalServerError(e : Exception): ResponseEntity<BaseResp> {
        return handleError(e.message, ErrorStatus.INTERNAL_SERVER_ERROR)
    }

    private fun handleError(message : String?, errorStatus : ErrorStatus): ResponseEntity<BaseResp> {
        val errorStatus = ErrorStatus.INTERNAL_SERVER_ERROR
        val errorResponse = BaseResp(null, errorStatus, message)
        return ResponseEntity(errorResponse, errorStatus.status)
    }
}