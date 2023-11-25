package com.meokq.api.application.service

import com.meokq.api.application.converter.BaseConverter
import com.meokq.api.application.converter.ImageConverter
import com.meokq.api.application.model.Image
import com.meokq.api.application.repository.ImageRepository
import com.meokq.api.application.request.ImageReq
import com.meokq.api.application.response.ImageResp
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.service.BaseService
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class ImageService(
    private val repository : ImageRepository,
    private val converter : ImageConverter,
) : BaseService<ImageReq, ImageResp, Image, String> {

    override var _repository: JpaRepository<Image, String> = repository
    override var _converter: BaseConverter<ImageReq, ImageResp, Image> = converter

    fun findById(id : String) : ImageResp {
        val model = repository.findById(id).orElseThrow{throw NotFoundException("image is not found!!")}
        return converter.modelToResponse(model)
    }
}