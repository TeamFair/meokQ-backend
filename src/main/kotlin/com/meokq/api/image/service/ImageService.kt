package com.meokq.api.image.service

import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import com.meokq.api.image.converter.ImageConverter
import com.meokq.api.image.model.Image
import com.meokq.api.image.repository.ImageRepository
import com.meokq.api.image.request.ImageReq
import com.meokq.api.image.response.ImageResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val repository : ImageRepository,
    private val converter : ImageConverter,
) : BaseService<ImageReq, ImageResp, Image, String> {

    override var _repository: JpaRepository<Image, String> = repository
    override var _converter: BaseConverter<ImageReq, ImageResp, Image> = converter

    override fun findById(id : String) : ImageResp {
        val model = repository.findById(id).orElseThrow{throw NotFoundException("image is not found!!")}
        return converter.modelToResponse(model)
    }
}