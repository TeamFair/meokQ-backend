package com.meokq.api.quest.request

import com.meokq.api.quest.enums.MissionType
import jakarta.validation.ValidationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class MissionReqTest {

    @Test
    fun validateNormal() {
        val normalMissionReq01 = MissionReq(
            content = null,
            target = null,
            quantity = null,
            type = MissionType.NORMAL
        )

        val normalMissionReq02 = MissionReq(
            content = null,
            target = "target",
            quantity = null,
            type = MissionType.NORMAL
        )

        val normalMissionReq03 = MissionReq(
            content = null,
            target = "target",
            quantity = 0,
            type = MissionType.NORMAL
        )

        val normalMissionReq04 = MissionReq(
            content = null,
            target = "target",
            quantity = 100,
            type = MissionType.NORMAL
        )

        Assertions.assertThrows(ValidationException::class.java){normalMissionReq01.validate()}
        Assertions.assertThrows(ValidationException::class.java){normalMissionReq02.validate()}
        Assertions.assertThrows(ValidationException::class.java){normalMissionReq03.validate()}
        Assertions.assertDoesNotThrow { normalMissionReq04.validate() }
    }

    @Test
    fun validateFree(){
        val freeMissionReq01 = MissionReq(
            content = null,
            target = null,
            quantity = null,
            type = MissionType.FREE
        )

        val freeMissionReq02 = MissionReq(
            content = "",
            target = null,
            quantity = null,
            type = MissionType.FREE
        )

        val freeMissionReq03 = MissionReq(
            content = " ",
            target = null,
            quantity = null,
            type = MissionType.FREE
        )

        val freeMissionReq04 = MissionReq(
            content = "content",
            target = null,
            quantity = null,
            type = MissionType.FREE
        )

        Assertions.assertThrows(ValidationException::class.java){freeMissionReq01.validate()}
        Assertions.assertThrows(ValidationException::class.java){freeMissionReq02.validate()}
        Assertions.assertThrows(ValidationException::class.java){freeMissionReq03.validate()}
        Assertions.assertDoesNotThrow { freeMissionReq04.validate() }
    }
}