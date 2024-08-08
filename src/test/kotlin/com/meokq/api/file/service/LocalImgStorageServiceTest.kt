package com.meokq.api.file.service

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
class LocalImgStorageServiceTest{
    @Autowired
    private lateinit var imgStorageService: ImgStorageService

    @Test
    @DisplayName("local 환경에서는 ImgLocalServiceImpl을 사용한다.")
    fun test(){
        assertTrue(imgStorageService is ImgLocalServiceImpl)
    }
}