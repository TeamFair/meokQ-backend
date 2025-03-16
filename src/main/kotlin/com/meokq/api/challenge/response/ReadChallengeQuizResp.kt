package com.meokq.api.challenge.response

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.quest.model.Quest
import com.meokq.api.user.model.Customer
import io.swagger.v3.oas.annotations.media.Schema
import lombok.Getter
import java.time.LocalDateTime

@Getter
class ReadChallengeQuizResp (
    model : Challenge,
    customer : Customer,
    missionTitle: String?,
    quest: Quest?
){
    @Schema(description = "Unique identifier for the challenge")
    val challengeId : String? = model.challengeId

    var missionTitle :String? = missionTitle

    var userNickName: String? = customer.nickname

    @Schema(description = "영수증 이미지 아이디")
    val receiptImageId : String? = model.receiptImageId

    @Schema(description = "도전 내역 상태")
    val status : ChallengeStatus = model.status

    @Schema(description = "챌린지 생성 시간")
    val createdAt : LocalDateTime = model.createDate!!

    @Schema(description = "좋아요 이모지 갯수")

    val likeCnt : Int = model.likeEmojiCnt

    @Schema(description = "싫어요 이모지 갯수")
    val hateCnt : Int = model.hateEmojiCnt

    @Schema(description = "조회수")
    val viewCount : Long = model.viewCount

    val questImage = quest?.imageId

    val answers = model.answers.map { it.content }
}