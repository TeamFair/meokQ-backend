package com.meokq.api.quest.response

import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.Quest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("local")
internal class QuestListRespTest{
    @Test
    @DisplayName("mission과 reward가 없을 때 quest의 응답값 변환에 문제가 생기지 않음.")
    fun convertToResponse(){
        Assertions.assertDoesNotThrow {
            val response = QuestListResp(
                Quest(
                    questId = "sample",
                    missions = null,
                    rewards = null,
                    type = QuestType.NORMAL,
                    target = QuestTarget.NONE,
                )
            )
            println(response.missionList.getOrNull(0)?.content)
            println(response.rewardList.toString())
        }

        Assertions.assertDoesNotThrow {
            val response = QuestListResp(
                Quest(
                    questId = "sample",
                    missions = mutableListOf(),
                    rewards = mutableListOf(),
                    type = QuestType.NORMAL,
                    target = QuestTarget.NONE,
                )
            )
            println(response.missionList.getOrNull(0)?.content)
            println(response.rewardList.toString())
        }
    }



}
