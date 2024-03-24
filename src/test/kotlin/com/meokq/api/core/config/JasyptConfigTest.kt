package com.meokq.api.core.config

import org.jasypt.encryption.StringEncryptor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
internal class JasyptConfigTest{

    @Autowired
    private lateinit var stringEncryptor: StringEncryptor

    @Test
    @DisplayName("암복호화 테스트")
    fun encryptTest(){
        // given
        val plainText = "plainText"

        // when
        /**
         * 같은 평문을 암호화할 때마다 항상 다른 암호문이 생성
         * - Jasypt는 매번 암호화를 할 때마다 무작위의 솔트와 초기화 벡터를 사용하여 다른 암호문을 생성
         *
         * 암호문이 다르더라도 올바른 솔트와 초기화 벡터가 제공되면 올바른 평문을 복호화할 수 있음.
         * - 복호화 과정에서는 각 암호문에 대해 적절한 솔트와 초기화 벡터를 사용하여 암호문을 해독
         */
        val encryptedText1 = stringEncryptor.encrypt(plainText)
        val encryptedText2 = stringEncryptor.encrypt(plainText)

        println("encrypted text : $encryptedText1")
        println("encrypted text : $encryptedText2")

        // then
        val decryptedText1 = stringEncryptor.decrypt(encryptedText1)
        val decryptedText2 = stringEncryptor.decrypt(encryptedText1)
        Assertions.assertEquals(plainText, decryptedText1)
        Assertions.assertEquals(plainText, decryptedText2)
    }

    @Test
    @DisplayName("시스템 프로퍼티를 읽어서 password에 할당")
    fun getPassword(){
        val password = System.getProperty("jasypt.encryptor.password")
        println(password)
        Assertions.assertNotNull(password)
    }
}