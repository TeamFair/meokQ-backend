package com.meokq.api.emoji.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.response.EmojiDefaultResp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
@ActiveProfiles("local")
@Rollback
class EmojiServiceTest{

    @Autowired
    private lateinit var emojiService: EmojiService

    val customerAuthReq =  AuthReq(
        userType = UserType.CUSTOMER,
        userId = "110804aa-a3f9-4894-93d9-9b446e583b27",
    )
    val customerAuthReq2 =  AuthReq(
        userType = UserType.CUSTOMER,
        userId = "4e7b25a2-5c65-4de4-82f7-f0034f5d4615",
    )
    val customerAuthReq3 =  AuthReq(
        userType = UserType.CUSTOMER,
        userId = "82eb81c2-7df9-4e47-9362-c71c6ac78f60",
    )
    val customerAuthReq4 =  AuthReq(
        userType = UserType.CUSTOMER,
        userId = "f6744202-f40f-4ce7-b00f-1a8d10456454",
    )

    val targetId = "CH10000001"
    lateinit var testSavedEmoji: EmojiDefaultResp

    @BeforeEach
    fun setUp() {
        // save 메서드 실행
        val req = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "like"
        )
        val req2 = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "like"
        )
        val req3 = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "like"
        )
        val req4 = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "like"
        )
        val req5 = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "hate"
        )
        val req6 = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "hate"
        )
        emojiService.register(customerAuthReq2,req2)
        emojiService.register(customerAuthReq3,req3)
        emojiService.register(customerAuthReq4,req4)
        emojiService.register(customerAuthReq,req5)
        emojiService.register(customerAuthReq2,req6)
        testSavedEmoji = emojiService.register(customerAuthReq,req)

    }

    @Test
    @DisplayName("LIKE 이모지를 등록 합니다.")
    fun saveLike() {
        val saveReq = EmojiRegisterReq(
            targetId = "CH10000002",
            targetType = "challenge",
            emojiType = "like"
        )
        val resp = emojiService.register(customerAuthReq,saveReq)

        assertNotNull(resp)
        assertEquals(saveReq.targetType.uppercase(),resp.targetType.toString())
        assertEquals(saveReq.emojiType.uppercase(),resp.emojiStatus.toString())
    }

    @Test
    @DisplayName("이모지는 중복될 수 없습니다.")
    fun saveLike2() {
        val req = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge",
            emojiType = "like"
        )
        // 같은 요청으로 두 번째 호출 시 예외 발생 예상
        val exception = assertThrows<NotUniqueException> {
            emojiService.register(customerAuthReq, req)
        }
        assertEquals("이미 등록된 이모지 입니다.", exception.message)
    }

    @Test
    @DisplayName("HATE 이모지를 등록 합니다.")
    fun saveHate() {
        val req = EmojiRegisterReq(
            targetId = "CH10000002",
            targetType = "challenge",
            emojiType = "hate"
        )
        val resp = emojiService.register(customerAuthReq,req)

        assertNotNull(resp)
        assertEquals(req.targetType.uppercase(),resp.targetType.toString())
        assertEquals(req.emojiType.uppercase(),resp.emojiStatus.toString())
    }

    @Test
    @DisplayName("유저가 Target에 이모지를 등록 했는지 나와야 합니다.")
    fun getEmoji() {
        val resp = emojiService.getEmoji(customerAuthReq,targetId)

        assertNotNull(resp)
        assertTrue(resp.isLike)
    }

    @Test
    @DisplayName("유저가 Target의 이모지를 삭제 합니다.")
    fun deleteEmoji() {
        emojiService.delete(customerAuthReq, testSavedEmoji.emojiId!!)

        val resp = emojiService.getEmoji(customerAuthReq,targetId)
        assertFalse(resp.isLike)
    }

    @Test
    @DisplayName("Target이 삭제되면 해당 이모지 모두가 삭제 됩니다.")

    fun deleteAllByTargetId(){
        emojiService.deleteAllByTargetId(targetId)
        val resp = emojiService.findAllByTargetId(targetId)

        assertTrue(resp.isEmpty())
    }

    @Test
    @DisplayName("Target에 등록된 이모지 갯수가 나와야 합니다.")
    fun countByTarget() {
        val resp = emojiService.countByTargetId(targetId)

        assertNotNull(resp)
        assertEquals(4,resp.likeEmojiCnt)
        assertEquals(2,resp.hateEmojiCnt)
    }
}