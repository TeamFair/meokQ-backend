package com.meokq.api.emoji.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.challenge.service.ChallengeReviewService
import com.meokq.api.challenge.service.ChallengeService
import com.meokq.api.coupon.service.CouponService
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.request.EmojiRegisterReq
import com.meokq.api.emoji.request.GetEmojiByTargetId
import com.meokq.api.emoji.response.EmojiResp
import com.meokq.api.quest.service.QuestService
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@Transactional
@SpringBootTest
@ActiveProfiles("local")
class EmojiServiceTest{

    @Autowired
    private lateinit var emojiService: EmojiService
    val customerAuthReq =  AuthReq(
        userType = UserType.CUSTOMER,
        userId = "110804aa-a3f9-4894-93d9-9b446e583b27",
    )

    val targetId = "CH10000001"

    @Test
    @DisplayName("LIKE 이모지를 등록 합니다.")
    fun saveLike() {
        val req = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge"
        )
        req.emojiStatus = EmojiStatus.LIKE
        val resp = emojiService.register(customerAuthReq,req)

        assertNotNull(resp)
        assertEquals(req.targetType.uppercase(),resp.targetType)
        assertEquals(req.emojiStatus.toString(),resp.emojiStatus)
    }

    @Test
    @DisplayName("HATE 이모지를 등록 합니다.")
    fun saveHate() {
        val req = EmojiRegisterReq(
            targetId = "CH10000001",
            targetType = "challenge"
        )
        req.emojiStatus = EmojiStatus.HATE
        val resp = emojiService.register(customerAuthReq,req)

        assertNotNull(resp)
        assertEquals(req.targetType.uppercase(),resp.targetType)
        assertEquals(req.emojiStatus.toString(),resp.emojiStatus)
    }

    @Test
    @DisplayName("유저가 Target에 이모지를 등록 했는지 나와야 합니다.")
    fun isEmoji() {
        val resp = emojiService.isEmoji(customerAuthReq,targetId)

        assertNotNull(resp)
        assertTrue(resp.isLike)
        assertTrue(resp.isHate)
    }


    @Test
    @DisplayName("유저가 Target의 이모지를 삭제 합니다.")
    fun deleteEmoji() {
        val emojiId = "f47ac10b-58cc-4372-a567-0e02b2c3d479"

        emojiService.delete(customerAuthReq,emojiId)

        val resp = emojiService.isEmoji(customerAuthReq,targetId)
        assertFalse(resp.isLike)
    }

    @Test
    @DisplayName("유저가 해당 이모지의 주인이 아니면 삭제할 수 없습니다.")
    fun deleteFalseEmoji() {
        val emojiId = "f47ac10b-58cc-4372-a567-0e02b2c3d479"

        emojiService.delete(customerAuthReq,emojiId)

        val resp = emojiService.isEmoji(customerAuthReq,targetId)
        assertFalse(resp.isLike)
    }

    @Test
    @DisplayName("해당 Target이 삭제되면 해당 이모지 모두가 삭제 됩니다.")
    fun deleteAllByTargetId(){
        emojiService.deleteAllByTargetId(targetId)
        val resp = emojiService.getModels(targetId)

        assertTrue(resp.isEmpty())
    }

    @Test
    @DisplayName("유저가 해당 Target에 이모지를 등록 하지 않았는지 나와야 합니다.")
    fun isEmojiFalse() {
        val customerAuthReq =  AuthReq(
            userType = UserType.CUSTOMER,
            userId = "f6744202-f40f-4ce7-b00f-1a8d10456454",
        )
        val resp = emojiService.isEmoji(customerAuthReq,targetId)

        assertNotNull(resp)
        assertTrue(resp.isLike)
        assertFalse(resp.isHate)
    }


    @Test
    @DisplayName("Target에 등록된 이모지 갯수가 나와야 합니다.")
    fun countByTarget() {
        val resp = emojiService.countByTarget(targetId)

        assertNotNull(resp)
        assertEquals(4,resp.likeEmojiCnt)
        assertEquals(2,resp.hateEmojiCnt)
    }
}