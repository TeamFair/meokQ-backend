package com.meokq.api.image.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.converter.BaseConverter
import com.meokq.api.core.converter.DateTimeConverter
import com.meokq.api.core.enums.DateTimePattern
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.core.service.BaseService
import com.meokq.api.image.converter.ImageConverter
import com.meokq.api.image.model.Image
import com.meokq.api.image.repository.ImageRepository
import com.meokq.api.image.request.ImageReq
import com.meokq.api.image.response.ImageResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class ImageService(
    private val repository : ImageRepository,
    private val converter : ImageConverter,
    private val dateTimeConverter: DateTimeConverter,
    private val storageService : ImgStorageService,

) : BaseService<ImageReq, ImageResp, Image, String> {

    override var _repository: JpaRepository<Image, String> = repository
    override var _converter: BaseConverter<ImageReq, ImageResp, Image> = converter

    override fun save(request: ImageReq, authReq: AuthReq): ImageResp {
        if (!request.type.createPermissions.contains(authReq.userType))
            throw InvalidRequestException("""
                ${authReq.userType}의 사용자는 ${request.type}타입의 이미지를 저장할 수 없습니다.
                (이미지 저장이 가능한 사용자 : ${request.type.createPermissions})
            """.trimIndent())

        // save image-file
        val fileName = generateImageFileName(request)
        storageService.uploadImage(fileName = fileName, imageReq = request)

        // save image-info
        val model = converter.requestToModel(request)
        val result = repository.save(model.apply {
            fileId = fileName
        })

        return converter.modelToResponse(result)
    }

    private fun generateImageFileName(request: ImageReq) : String{
        val timestamp = dateTimeConverter.convertToString(LocalDateTime.now(), DateTimePattern.COMPACT)
        val random = String.format("%02d", Random.nextInt(0, 100))
        return "IM${request.type.prefix}${timestamp}${random}"
    }

    override fun deleteById(id: String, authReq: AuthReq) {
        val model = findModelById(id)

        if (model.type?.createPermissions?.contains(authReq.userType) == false)
            throw InvalidRequestException("""
                ${authReq.userType}의 사용자는 ${model.type}타입의 이미지를 삭제할 수 없습니다.
                (이미지 삭제가 가능한 사용자 : ${model.type?.createPermissions})
            """.trimIndent())

        storageService.deleteImage(id)
        super.deleteById(id, authReq)
    }

    // TODO : 처리방법 조회
    override fun findById(id: String, authReq: AuthReq): ImageResp {
        val model = findModelById(id)

        if (model.type?.createPermissions?.contains(authReq.userType) == false)
            throw InvalidRequestException("""
                ${authReq.userType}의 사용자는 ${model.type}타입의 이미지를 조회할 수 없습니다.
                (이미지 조회가 가능한 사용자 : ${model.type?.createPermissions})
            """.trimIndent())

        return ImageResp(model)
    }

    fun downloadImage(fileName: String): ByteArray {
        return storageService.downloadImage(fileName)
    }
}