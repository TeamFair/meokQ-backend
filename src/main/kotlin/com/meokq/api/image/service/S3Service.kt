package com.meokq.api.image.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.IOException

@Service
class S3Service(
    private val s3Client: S3Client
) {
    @Value("\${cloud.bucket.name}")
    val bucketName: String? = null

    fun uploadImage(file: MultipartFile, key: String) {
        try {
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.contentType)
                .build()

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.bytes))
        } catch (e: IOException) {
            // 처리 중 발생한 예외 처리
            e.printStackTrace()
        }
    }

    fun downloadImage(key: String): ByteArray {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        val responseBytes: ResponseBytes<*> = s3Client.getObjectAsBytes(getObjectRequest)
        return responseBytes.asByteArray()
    }

    fun deleteImage(key: String) {
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build()

        s3Client.deleteObject(deleteObjectRequest)
    }
}