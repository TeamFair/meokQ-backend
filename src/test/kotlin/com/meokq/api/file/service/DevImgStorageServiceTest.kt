package com.meokq.api.file.service

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("dev")
class DevImgStorageServiceTest{
    @Autowired
    private lateinit var imgStorageService: ImgStorageService

    @Test
    @DisplayName("dev 환경에서는 ImgS3ServiceImpl을 사용한다.")
    fun test(){
        Assertions.assertTrue(imgStorageService is ImgS3ServiceImpl)
    }
}