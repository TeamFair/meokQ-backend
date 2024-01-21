package com.meokq.api.image.service

import com.meokq.api.image.request.ImageReq
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import software.amazon.awssdk.core.ResponseBytes
import software.amazon.awssdk.core.exception.SdkException
import software.amazon.awssdk.core.sync.RequestBody.fromBytes
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.FileNotFoundException
import java.io.IOException

@Service
@Profile("dev")
class ImgS3ServiceImpl(
    private val s3Client: S3Client
) : ImgStorageService {
    @Value("\${cloud.bucket.name}")
    private val bucketName: String? = null

    override fun uploadImage(fileName: String, imageReq: ImageReq) {
        try {
            val file = imageReq.file
            val putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key("$fileName")
                .contentType(file.contentType)
                .build()

            s3Client.putObject(putObjectRequest, fromBytes(file.bytes))
        } catch (e: IOException) {
            // 처리 중 발생한 예외 처리
            e.printStackTrace()
            throw e
        }
    }

    override fun downloadImage(fileName: String): ByteArray {
        val getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        try {
            val responseBytes: ResponseBytes<*> = s3Client.getObjectAsBytes(getObjectRequest)
            return responseBytes.asByteArray()
        } catch (e: SdkException) {
            // S3에서 이미지를 찾을 수 없는 경우
            e.printStackTrace()
            throw FileNotFoundException("File not found in S3: $fileName")
        } catch (e: IOException) {
            // 처리 중 발생한 예외 처리
            e.printStackTrace()
            throw e
        }
    }

    override fun deleteImage(fileName: String) {
        val deleteObjectRequest = DeleteObjectRequest.builder()
            .bucket(bucketName)
            .key(fileName)
            .build()

        try {
            s3Client.deleteObject(deleteObjectRequest)
        } catch (e: SdkException) {
            // S3에서 이미지를 삭제할 수 없는 경우
            e.printStackTrace()
            throw IOException("Error deleting file from S3: $fileName")
        }
    }
}