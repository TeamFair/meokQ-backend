package com.meokq.api.image.service

import com.meokq.api.image.enums.ImageType
import com.meokq.api.image.request.ImageReq
import io.jsonwebtoken.lang.Assert
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.context.ActiveProfiles
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
class ImgLocalServiceTest {
    @Value("\${file.upload-dir}")
    private lateinit var uploadDir: String

    @Autowired
    private lateinit var imgLocalServiceImpl: ImgLocalServiceImpl

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
        imgLocalServiceImpl.uploadImage(
            fileName = testFileKey,
            imageReq = ImageReq(type = ImageType.MARKET_LOGO, file = testFile)
        )

        // 이미지 다운로드
        val downloadedImage = imgLocalServiceImpl.downloadImage(testFileKey)

        // 다운로드된 이미지 검증
        Assert.isTrue(
            downloadedImage.contentEquals(testFile.bytes),
            "Downloaded image content does not match the original file."
        )

        // 이미지 삭제
        imgLocalServiceImpl.deleteImage(testFileKey)

        // 이미지가 삭제되었는지 확인
        val filePath: Path = Paths.get(uploadDir).resolve(testFileKey)
        val deletedImageExists = Files.exists(filePath)

        Assert.isTrue(!deletedImageExists, "Image was not deleted successfully.")
    }
}