package com.meokq.api.image.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.image.enums.ImageType
import com.meokq.api.image.model.Image
import com.meokq.api.image.request.ImageReq
import com.meokq.api.image.response.ImageResp
import com.meokq.api.image.service.ImageService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Image", description = "이미지")
@RestController
@RequestMapping("/api/images")
class ImageController(
    private val service : ImageService
) :BaseController<ImageReq, ImageResp, Image, String> {
    override val _service: BaseService<ImageReq, ImageResp, Image, String> = service

    @Operation(
        summary = "이미지 단건 등록",
        description = "이미지를 등록합니다."
    )
    @PostMapping(consumes = ["multipart/form-data"])
    fun save(
        @RequestParam(name = "file") file: MultipartFile,
        @RequestParam(name = "type") type: ImageType
    ): ResponseEntity<BaseResp> {
        // TODO: 파일 처리
        return super.save(
            ImageReq(
                type = type,
                originalFilename = file.originalFilename,
                size = file.size
            )
        )
    }

    @Operation(
        summary = "이미지 정보 삭제",
        description = "이미지 정보를 삭제합니다.",
        parameters = [
            Parameter(name = "imageId", description = "이미지 아이디", required = true),
        ]
    )
    @DeleteMapping("/{imageId}")
    override fun deleteById(@PathVariable imageId: String) : ResponseEntity<BaseResp> {
        return super.deleteById(imageId)
    }
}