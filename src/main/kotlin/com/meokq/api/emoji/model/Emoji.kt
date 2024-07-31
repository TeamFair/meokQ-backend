package com.meokq.api.emoji.model

import com.meokq.api.core.model.BaseDateTimeModel
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.core.enums.TargetType
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.emoji.request.EmojiRegisterReq
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_emoji")
@Table(
    name = "tb_emoji",
    indexes = [
        Index(name = "tb_emojiId_userId", columnList = "emojiId, userId"),
    ],
)
data class Emoji(
    @Id
    @UuidGenerator
    var emojiId : String? = null,

    @Enumerated(EnumType.STRING)
    val status : EmojiStatus,

    @Embedded
    val targetMetadata: TargetMetadata

): BaseDateTimeModel() {

    constructor(status: EmojiStatus, req: EmojiRegisterReq, reqUserId: String ) : this(
        emojiId = null,
        status = status,
        targetMetadata =  TargetMetadata(
            targetId = req.targetId,
            targetType = TargetType.fromString(req.targetType),
            userId = reqUserId)

    )
}
