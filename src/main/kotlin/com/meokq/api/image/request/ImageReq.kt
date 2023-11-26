package com.meokq.api.image.request

import com.meokq.api.image.enums.ImageType
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Image-Request")
class ImageReq(
    @Schema(description = "이미지 종류")
    val type : ImageType,

    @Schema(description = "파일 이름")
    val originalFilename : String?,

    @Schema(description = "파일 크기")
    val size : Long,
)