package com.meokq.api.quest.request

import com.meokq.api.quest.enums.MissionType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.model.Reward
import io.swagger.v3.oas.annotations.media.Schema

data class QuestCreateReq(
    @Schema(example = "MK00000001")
    val marketId: String, // TODO : 추후 제거
    val missions: List<MissionReq>,
    val rewards: List<RewardReq>,
    val score: Int = 0,
    @Schema(description = "미션 대상", example = "")
    val target: String?,
    @Schema(description = "미션 종류", example = "FREE")
    val type: String?,
    /*@Schema(description = "만료 시간")
    @field:Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.")
    val expireDate : String? = null*/
) {
    fun toEntity(): Quest {
        return Quest(
            marketId = this.marketId,
            missions = this.missions.map { Mission(it) },
            rewards = this.rewards.map { Reward(it) },
            target = QuestTarget.valueOfWithThrow(target),
            type = QuestType.valueOfWithThrow(type),
        )
    }
}
