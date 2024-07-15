package com.meokq.api.file.controller

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.file.annotations.ExplainDeleteImage
import com.meokq.api.file.annotations.ExplainSaveImage
import com.meokq.api.file.annotations.ExplainSelectImage
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.request.ImageReq
import com.meokq.api.file.service.ImageService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.*
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Image", description = "이미지")
@RequestMapping("/api")
@RestController
class ImageController(
    private val service : ImageService
) : ResponseEntityCreation, AuthDataProvider {

    @ExplainSaveImage
    @Transactional(rollbackFor = [Exception::class])
    @PostMapping(value = ["/customer/image", "/boss/image","/admin/image"], consumes = ["multipart/form-data"])
    fun save(
        @RequestParam(name = "file") file: MultipartFile,
        @RequestParam(name = "type") type: ImageType
    ): ResponseEntity<BaseResp> {
        return getRespEntity(
            service.save(
                request = ImageReq(type = type, file = file),
                authReq = getAuthReq()
            )
        )
    }

    @ExplainSelectImage
    @GetMapping(value = ["/customer/image/{imageId}", "/boss/image/{imageId}", "/admin/image/{imageId}", "/open/image/{imageId}"])
    fun downloadImage(@PathVariable imageId: String): ResponseEntity<ByteArray> {
        val imageData = service.downloadImage(imageId)

        // Content-Type 설정
        val contentType: MediaType = MediaType.IMAGE_JPEG
        val headers = HttpHeaders()
        headers.contentType = contentType

        // Content-Disposition 설정 (파일 다운로드를 위한 설정)
        headers.contentDisposition = ContentDisposition
            .builder("inline")
            .filename("$imageId.${contentType.subtype}")
            .build()

        return ResponseEntity(imageData, headers, HttpStatus.OK)
    }

    @ExplainDeleteImage
    @Transactional(rollbackFor = [Exception::class])
    @DeleteMapping("/customer/image/{imageId}", "/admin/image/{imageId}", "/boss/image/{imageId}")
    fun deleteById(@PathVariable imageId: String): ResponseEntity<BaseResp> {
        return getRespEntity(
            service.deleteById(
                id = imageId,
                authReq = getAuthReq()
            )
        )
    }
}