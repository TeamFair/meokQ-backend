package com.meokq.api.image.response

import com.meokq.api.image.model.Image
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Image-Response")
class ImageResp(
    @Schema(description = "이미지 ID")
    val imageId : String?,

    @Schema(description = "이미지 주소")
    val location : String? = null,
){
    constructor(model: Image) : this(
        imageId = model.fileId,
        location = model.location
    )
}