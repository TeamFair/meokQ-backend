package com.meokq.api.image.request

import com.meokq.api.image.enums.ImageType
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

@Schema(name = "Image-Request")
class ImageReq(
    @Schema(description = "이미지 종류")
    val type : ImageType,

    @Schema(description = "file")
    val file: MultipartFile
)