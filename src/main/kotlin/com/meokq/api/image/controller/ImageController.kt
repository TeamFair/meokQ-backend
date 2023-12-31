package com.meokq.api.image.controller

import com.meokq.api.core.controller.BaseController
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.core.service.BaseService
import com.meokq.api.image.annotations.ExplainDeleteImage
import com.meokq.api.image.annotations.ExplainSaveImage
import com.meokq.api.image.annotations.ExplainSelectImage
import com.meokq.api.image.enums.ImageType
import com.meokq.api.image.model.Image
import com.meokq.api.image.request.ImageReq
import com.meokq.api.image.response.ImageResp
import com.meokq.api.image.service.ImageService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Image", description = "이미지")
@RequestMapping("/api")
@RestController
class ImageController(
    private val service : ImageService
) :BaseController<ImageReq, ImageResp, Image, String> {
    override val _service: BaseService<ImageReq, ImageResp, Image, String> = service

    @ExplainSaveImage
    @PostMapping(value = ["/customer/image", "/boss/image"], consumes = ["multipart/form-data"])
    fun save(
        @RequestParam(name = "file") file: MultipartFile,
        @RequestParam(name = "type") type: ImageType
    ): ResponseEntity<BaseResp> {
        return super.saveWithAuth(ImageReq(type = type, file = file,))
    }

    @ExplainSelectImage
    @GetMapping(value = ["/customer/image/{imageId}", "/boss/image/{imageId}", "/admin/image/{imageId}", "/open/image/{imageId}", ])
    override fun findById(@PathVariable imageId: String): ResponseEntity<BaseResp> {
        return super.findByIdWithAuth(imageId)
    }

    @ExplainDeleteImage
    @DeleteMapping("/customer/image/{imageId}", "/admin/image/{imageId}", "/boss/image/{imageId}")
    override fun deleteById(@PathVariable imageId: String): ResponseEntity<BaseResp> {
        return super.deleteByIdWithAuth(imageId)
    }
}