package com.meokq.api.challenge.model

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.core.model.BaseDateTimeModel
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.response.EmojiResp
import jakarta.persistence.*
import lombok.EqualsAndHashCode
import org.apache.commons.lang3.builder.EqualsExclude
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import java.time.LocalDateTime

@Entity
@Table(name = "tb_challenge_history")
data class Challenge(
    @Id
    /*@GenericGenerator(
        name = "Seq_GEN_CHALLENGE",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "CHALLENGE_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_GEN_CHALLENGE")*/
    @UuidGenerator
    var challengeId: String? = null,
    @Enumerated(EnumType.STRING)
    var status: ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    var rejectReason: String? = null,
    var questId: String? = null,
    var customerId: String? = null,
    val receiptImageId: String? = null,
    var likeEmojiCnt: Int = 0,
    var hateEmojiCnt: Int = 0,
    var viewCount: Long = 0,
    /*@ManyToOne
    @JoinColumn(name = "customer_id")
    var customer : Customer? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    var quest : Quest? = null,

    @OneToOne
    @JoinColumn(name = "receipt_image_id")
    val receiptImage : Image? = null,*/
    @CreationTimestamp
    var createDate: LocalDateTime? = null,

    @UpdateTimestamp
    var updateDate: LocalDateTime? = null
) {
    constructor(request: ChallengeSaveReq) : this(
        receiptImageId = request.receiptImageId,
        questId = request.questId,
    )

    fun appendEmojiCnt(emojiResp: EmojiResp) {
        this.likeEmojiCnt = emojiResp.likeEmojiCnt
        this.hateEmojiCnt = emojiResp.hateEmojiCnt
    }

    fun increaseViewCount() {
        this.viewCount++
    }

    fun updateStatus(status: ChallengeStatus) {
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
