package com.meokq.api.challenge.response

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.model.Challenge
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.EmojiStatus.*
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.response.EmojiResp
import com.meokq.api.quest.response.QuestResp
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

class ReadChallengeResp(
    model : Challenge
){
    @Schema(description = "Unique identifier for the challenge")
    val challengeId : String? = model.challengeId

    var userNickName: String? = null

    // TODO : 확인필요.
    @Schema(description = "퀘스트 정보")
    var missionTitle :String? = null

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


}
