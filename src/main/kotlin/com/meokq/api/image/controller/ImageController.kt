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
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Image", description = "이미지")
@RestController
@RequestMapping("/api")
class ImageController(
    private val service : ImageService
) :BaseController<ImageReq, ImageResp, Image, String> {
    override val _service: BaseService<ImageReq, ImageResp, Image, String> = service

    @Operation(
        summary = "(IIM001) 이미지 단건 등록",
        description = "이미지를 등록합니다."
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "data": {
                    "imageId": "IM2023122418180655",
                    "location": "https://ifh.cc/v-pORvTP"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    @PostMapping(value = ["/customer/image", "/boss/image"], consumes = ["multipart/form-data"])
    fun save(
        @RequestParam(name = "file") file: MultipartFile,
        @RequestParam(name = "type") type: ImageType
    ): ResponseEntity<BaseResp> {
        // TODO: 파일 처리
        return super.saveWithAuth(
            ImageReq(
                type = type,
                file = file,
            )
        )
    }

    @Operation(
        summary = "(IIM002) 이미지 정보 조회",
        description = "이미지 정보를 조회합니다.",
        parameters = [
            Parameter(name = "imageId", description = "이미지 아이디", required = true, example = "IM10000004"),
        ]
    )
    @GetMapping(
        value = [
            "/customer/image/{imageId}",
            "/boss/image/{imageId}",
            "/admin/image/{imageId}",
            "/open/image/{imageId}",
        ]
    )
    @ApiResponse(
        responseCode = "500",
        description = "잘못된 요청",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "errMessage": "UNKNOWN의 사용자는 BUSINESS_REGISTRATION_CERTIFICATE타입의 이미지를 조회할 수 없습니다.\n(이미지 조회가 가능한 사용자 : [BOSS])",
                  "status": "INTERNAL_SERVER_ERROR",
                  "message": "An unknown error occurred."
                }
            """)])]
    )
    @ApiResponse(
        responseCode = "200",
        description = "성공",
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = BaseResp::class),
            examples = [ExampleObject(value = """
                {
                  "data": {
                    "imageId": "IM2023122418180655",
                    "location": "https://ifh.cc/v-pORvTP"
                  },
                  "status": "OK",
                  "message": "Your request has been processed successfully."
                }
            """)])]
    )
    override fun findById(
        @PathVariable imageId: String
    ): ResponseEntity<BaseResp> {
        return super.findByIdWithAuth(imageId)
    }

    @Operation(
        summary = "(IIM003) 이미지 정보 삭제",
        description = "이미지 정보를 삭제합니다.",
        parameters = [
            Parameter(name = "imageId", description = "이미지 아이디", required = true, example = "IM10000004"),
        ]
    )
    @DeleteMapping("/customer/admin/{imageId}")
    override fun deleteById(
        @PathVariable imageId: String
    ): ResponseEntity<BaseResp> {
        return super.deleteByIdWithAuth(imageId)
    }
}