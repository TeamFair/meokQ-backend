package com.meokq.api.image.converter

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.image.model.Image
import com.meokq.api.image.request.ImageReq
import com.meokq.api.image.response.ImageResp
import org.springframework.stereotype.Component

@Component
class ImageConverter : BaseConverter<ImageReq, ImageResp, Image> {
    override fun modelToResponse(model: Image): ImageResp {
        return ImageResp(
            imageId = model.imageId,
            location = model.location
        )
    }

    override fun requestToModel(request: ImageReq): Image {
        return Image(
            type = request.type,
            originalFilename = request.originalFilename,
            size = request.size,
        )
    }
}