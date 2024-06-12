package com.meokq.api.challenge.model

import com.meokq.api.challenge.enums.ChallengeStatus
import com.meokq.api.challenge.request.ChallengeSaveReq
import com.meokq.api.core.model.BaseModel
import com.meokq.api.emoji.model.Emoji
import com.meokq.api.emoji.response.EmojiResp
import jakarta.persistence.*
import lombok.Getter
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_challenge_history")
data class Challenge(
    @Id
    /*@GenericGenerator(
        name = "Seq_GEN_CHALLENGE",
        strategy = "com.meokq.api.core.idgen.SeqIdGenerator",
        parameters = [Parameter(name="seqGenerator", value= "CHALLENGE_ID")]
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Seq_GEN_CHALLENGE")*/
    @UuidGenerator
    var challengeId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : ChallengeStatus = ChallengeStatus.UNDER_REVIEW,
    var rejectReason : String? = null,
    var questId : String? = null,
    var customerId : String? = null,
    val receiptImageId : String? = null,
    var likeEmojiCnt : Int = 0,
    var hateEmojiCnt : Int = 0,

    /*@ManyToOne
    @JoinColumn(name = "customer_id")
    var customer : Customer? = null,

    @ManyToOne
    @JoinColumn(name = "quest_id")
    var quest : Quest? = null,

    @OneToOne
    @JoinColumn(name = "receipt_image_id")
    val receiptImage : Image? = null,*/
) : BaseModel(){

    constructor(request: ChallengeSaveReq): this(
        receiptImageId = request.receiptImageId,
        questId = request.questId,
    )

    fun appendEmojiCnt(emojiResp: EmojiResp){
        this.likeEmojiCnt = emojiResp.likeEmojiCnt
        this.hateEmojiCnt = emojiResp.hateEmojiCnt
    }
}
