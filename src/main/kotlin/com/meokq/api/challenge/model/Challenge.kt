package com.meokq.api.challenge.model

import com.meokq.api.answer.model.AnswerHistoryEntity
import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.response.EmojiResp
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
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

    @OneToMany(cascade = [CascadeType.PERSIST], orphanRemoval = true)
    @JoinColumn(name = "challenge_id")
    var answers: MutableList<AnswerHistoryEntity> = mutableListOf(),

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

    fun increaseEmojiCnt(emojiType: EmojiStatus) {
        if (emojiType == EmojiStatus.HATE) {
            hateEmojiCnt++
        } else {
            likeEmojiCnt++
        }
    }

    fun decreaseEmojiCnt(emojiType: EmojiStatus) {
        if (emojiType == EmojiStatus.HATE) {
            hateEmojiCnt--
            if (hateEmojiCnt < 0) hateEmojiCnt = 0
        } else {
            likeEmojiCnt--
            if (likeEmojiCnt < 0) likeEmojiCnt = 0
        }
    }
}
