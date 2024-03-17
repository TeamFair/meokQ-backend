package com.meokq.api.file.response

import com.meokq.api.file.model.Image
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "Image-Response")
class ImageResp(
    @Schema(description = "이미지 ID")
    val imageId : String?,

    @Schema(description = "이미지 주소")
    val location : String? = "/open/images/${imageId}"
){
    constructor(model: Image) : this(
        imageId = model.fileId,
        location = "/open/images/${model.fileId}"
    )
}