package com.meokq.api.file.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.core.converter.DateTimeConverterV2.convertToString
import com.meokq.api.core.enums.DateTimePattern
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.file.model.Image
import com.meokq.api.file.repository.ImageRepository
import com.meokq.api.file.request.ImageReq
import com.meokq.api.file.response.ImageResp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class ImageService(
    private val repository : ImageRepository,
    //private val storageService : ImgStorageService,
    // TODO: local 포트를 제거하지 건까지 우선 S3와 연결
    private val storageService : ImgS3ServiceImpl,
) : JpaService<Image, String> {
    override var jpaRepository: JpaRepository<Image, String> = repository

    fun save(request: ImageReq, authReq: AuthReq): ImageResp {
        if (!request.type.createPermissions.contains(authReq.userType))
            throw InvalidRequestException("""
                ${authReq.userType}의 사용자는 ${request.type}타입의 이미지를 저장할 수 없습니다.
                (이미지 저장이 가능한 사용자 : ${request.type.createPermissions})
            """.trimIndent())

        // file-name 채번
        val fileName = generateImageFileName(request)

        // save image-info
        val model = Image(request, fileName)
        val result = repository.save(model.apply {
            fileId = fileName
        })

        // save image-file
        storageService.uploadImage(fileName = fileName, imageReq = request)

        return ImageResp(result)
    }

    private fun generateImageFileName(request: ImageReq) : String{
        val timestamp = convertToString(LocalDateTime.now(), DateTimePattern.COMPACT)
        val random = String.format("%02d", Random.nextInt(0, 100))
        return "IM${request.type.prefix}${timestamp}${random}"
    }

    fun deleteById(id: String, authReq: AuthReq) {
        val model = findModelById(id)

        if (model.type?.createPermissions?.contains(authReq.userType) == false)
            throw InvalidRequestException("""
                ${authReq.userType}의 사용자는 ${model.type}타입의 이미지를 삭제할 수 없습니다.
                (이미지 삭제가 가능한 사용자 : ${model.type?.createPermissions})
            """.trimIndent())

        storageService.deleteImage(id)
        deleteById(id)
    }

    // TODO : 처리방법 조회
    fun findById(id: String, authReq: AuthReq): ImageResp {
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