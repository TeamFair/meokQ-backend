package com.meokq.api.quest.request

import com.meokq.api.quest.enums.QuestTarget
import com.meokq.api.quest.enums.QuestType
import com.meokq.api.quest.model.Mission
import com.meokq.api.quest.model.Quest
import com.meokq.api.quest.model.Reward
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Pattern
import java.time.LocalDate

data class QuestUpdateReq(
    val writer : String,
    val imageId : String,
    val missions : List<MissionReq>,
    val rewards : List<RewardReq>,
    @Schema(description = "만료 시간")
    @field:Pattern(regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])", message = "날짜 형식은 yyyy-MM-dd 이어야 합니다.")
    val expireDate : String,
    val score : Int,
    @Schema(description = "미션 대상", example = "")
    val target: String?,
    @Schema(description = "미션 종류", example = "FREE")
    val type: String,
    val mainImageId: String? = null,
    val popularYn : Boolean = false,
) {
    fun toEntity(): Quest {
        return Quest(
            writer = this.writer,
            imageId = this.imageId,
            missions = this.missions.map { Mission(it) }.toMutableList(),
            rewards = this.rewards.map { Reward(it) }.toMutableList(),
            expireDate = LocalDate.parse(this.expireDate).atTime(0, 0,0 ),
            score = this.score,
            target = QuestTarget.valueOfWithThrow(target),
            type = QuestType.valueOfWithThrow(type),
            mainImageId = this.mainImageId,
            popularYn = this.popularYn,
        )
    }
}
