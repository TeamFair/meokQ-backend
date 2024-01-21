package com.meokq.api.image.service

import com.meokq.api.image.enums.ImageType
import com.meokq.api.image.request.ImageReq
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.Assert
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response
import java.util.*

@SpringBootTest
@ActiveProfiles("dev")
class S3ImgServiceTest {

    @Autowired
    private lateinit var s3Service: ImgS3ServiceImpl

    @Autowired
    private lateinit var s3Client: S3Client

    @Value("\${cloud.bucket.name}")
    val bucketName: String? = null

    @Test
    fun testUploadDownloadDeleteImage() {
        // 업로드할 테스트 파일 생성
        val testFileKey = "test-image-${UUID.randomUUID()}.png"
        val testFile = MockMultipartFile(
            "test-image.png",
            "test-image.png",
            "image/png",
            "Hello, World!".toByteArray()
        )

        // 이미지 업로드
        s3Service.uploadImage(
            fileName = testFileKey,
            imageReq = ImageReq(type = ImageType.MARKET_LOGO, file = testFile)
        )

        // 이미지 다운로드
        val downloadedImage = s3Service.downloadImage(testFileKey)

        // 다운로드된 이미지 검증
        Assert.isTrue(
            downloadedImage.contentEquals(testFile.bytes),
            "Downloaded image content does not match the original file."
        )

        // 이미지 삭제
        s3Service.deleteImage(testFileKey)

        // 이미지가 삭제되었는지 확인
        val listObjectsRequest =
            ListObjectsV2Request.builder().bucket(bucketName).build()
        val listObjectsResponse: ListObjectsV2Response = s3Client.listObjectsV2(listObjectsRequest)
        val deletedImageExists = listObjectsResponse.contents().any { it.key() == testFileKey }

        Assert.isTrue(!deletedImageExists, "Image was not deleted successfully.")
    }
}