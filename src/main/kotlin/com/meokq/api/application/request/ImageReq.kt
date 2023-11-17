package com.meokq.api.application.request

import com.meokq.api.application.enums.ImageType
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