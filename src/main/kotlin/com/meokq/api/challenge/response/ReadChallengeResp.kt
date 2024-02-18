package com.meokq.api.challenge.response

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.quest.response.QuestResp
import io.swagger.v3.oas.annotations.media.Schema

class ReadChallengeResp(
    model : Challenge
){
    @Schema(description = "Unique identifier for the challenge")
    val challengeId : String? = model.challengeId

    var userNickName: String? = null

    // TODO : 확인필요.
    @Schema(description = "퀘스트 정보")
    var quest : QuestResp? = null

    @Schema(description = "영수증 이미지 아이디")
    val receiptImageId : String? = model.receiptImageId

    @Schema(description = "도전 내역 상태")
    val status : ChallengeStatus = model.status
}
