package com.meokq.api.challenge.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.meokq.api.quest.response.QuestResp
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Challenge-Response")
class ChallengeResp(
    @Schema(description = "Unique identifier for the challenge")
    val challengeId : String?,

    // TODO : 확인필요.
    @Schema(description = "퀘스트 정보")
    @JsonIgnore
    var quest : QuestResp?,

    @Schema(description = "영수증 이미지의 주소")
    val receiptImage : String?,
)