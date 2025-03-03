package com.meokq.api.challenge.model

import com.meokq.api.answer.model.AnswerHistoryEntity
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.emoji.response.EmojiResp
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import java.time.LocalDateTime

@Entity
@Table(name = "tb_challenge_history")
data class Challenge(
    @Id
    @UuidGenerator
    var challengeId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    var rejectReason : String? = null,
    var questId : String? = null,
    var customerId : String? = null,
    val receiptImageId : String? = null, // todo : answers 안으로 옮기는 건 어떤지이...
    var likeEmojiCnt : Int = 0,
    var hateEmojiCnt : Int = 0,
    var viewCount : Long = 0,

    @OneToMany
    @JoinColumn(name = "challenge_id")
    var answers: MutableList<AnswerHistoryEntity> = listOf(),

    @CreationTimestamp
    var createDate : LocalDateTime? = null,

    @UpdateTimestamp
    var updateDate : LocalDateTime? = null
){
    constructor(request: ChallengeSaveReq): this(
        receiptImageId = request.receiptImageId,
        questId = request.questId,
    )

    fun appendEmojiCnt(emojiResp: EmojiResp){
        this.likeEmojiCnt = emojiResp.likeEmojiCnt
        this.hateEmojiCnt = emojiResp.hateEmojiCnt
    }

    fun increaseViewCount(){
        this.viewCount++
    }

    fun updateStatus(status: ChallengeStatus){
        this.status = status
    }
}
