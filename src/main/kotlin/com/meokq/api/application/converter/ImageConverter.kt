package com.meokq.api.application.converter

import com.meokq.api.application.model.Image
import com.meokq.api.application.request.ImageReq
import com.meokq.api.application.response.ImageResp
import org.springframework.stereotype.Component

@Component
class ImageConverter : BaseConverter<ImageReq, ImageResp, Image> {
    override fun modelToResponse(model: com.meokq.api.application.model.Image): ImageResp {
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