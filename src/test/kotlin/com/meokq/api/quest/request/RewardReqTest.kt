package com.meokq.api.quest.request

import com.meokq.api.quest.enums.RewardType
import jakarta.validation.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class RewardReqTest {

    @Test
    fun validateGift() {
        val giftRewardReq01 = RewardReq(
            content = null,
            target = null,
            quantity = null,
            discountRate = null,
            type = RewardType.GIFT,
        )

        val giftRewardReq02 = RewardReq(
            content = null,
            target = "target",
            quantity = null,
            discountRate = null,
            type = RewardType.GIFT,
        )

        val giftRewardReq03 = RewardReq(
            content = null,
            target = "target",
            quantity = 0,
            discountRate = null,
            type = RewardType.GIFT,
        )

        val giftRewardReq04 = RewardReq(
            content = null,
            target = "target",
            quantity = 200,
            discountRate = null,
            type = RewardType.GIFT,
        )

        val giftRewardReq05 = RewardReq(
            content = null,
            target = "target",
            quantity = 10,
            discountRate = null,
            type = RewardType.GIFT,
        )

        Assertions.assertThrows(ValidationException::class.java){giftRewardReq01.validate() }
        Assertions.assertThrows(ValidationException::class.java){giftRewardReq02.validate() }
        Assertions.assertThrows(ValidationException::class.java){giftRewardReq03.validate() }
        Assertions.assertThrows(ValidationException::class.java){giftRewardReq04.validate() }
        Assertions.assertDoesNotThrow { giftRewardReq05.validate() }
    }

    @Test
    fun validateDiscount() {
        val discountRewardReq01 = RewardReq(
            content = null,
            target = null,
            quantity = null,
            discountRate = null,
            type = RewardType.DISCOUNT,
        )

        val discountRewardReq02 = RewardReq(
            content = null,
            target = "target",
            quantity = null,
            discountRate = null,
            type = RewardType.DISCOUNT,
        )

        val discountRewardReq03 = RewardReq(
            content = null,
            target = "target",
            quantity = null,
            discountRate = 100,
            type = RewardType.DISCOUNT,
        )

        val discountRewardReq04 = RewardReq(
            content = null,
            target = "target",
            quantity = null,
            discountRate = -100,
            type = RewardType.DISCOUNT,
        )

        val discountRewardReq05 = RewardReq(
            content = null,
            target = "target",
            quantity = null,
            discountRate = 99,
            type = RewardType.DISCOUNT,
        )

        Assertions.assertThrows(ValidationException::class.java){discountRewardReq01.validate()}
        Assertions.assertThrows(ValidationException::class.java){discountRewardReq02.validate()}
        Assertions.assertThrows(ValidationException::class.java){discountRewardReq03.validate()}
        Assertions.assertThrows(ValidationException::class.java){discountRewardReq04.validate()}
        Assertions.assertDoesNotThrow { discountRewardReq05.validate() }
    }
}