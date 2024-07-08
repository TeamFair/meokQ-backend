package com.meokq.api.emoji.model

import com.meokq.api.core.model.BaseDateTimeModel
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
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
    var status : EmojiStatus? = null,

    var userId : String? = null,

    @Enumerated(EnumType.STRING)
    var targetType : TargetType? = null,

    var targetId : String? = null
): BaseDateTimeModel()
{
    fun like(req:EmojiRegisterReq, reqUserId: String) = Emoji (
        targetId = req.targetId,
        targetType = convertStatusNameToEnum(req.targetType),
        userId = reqUserId,
        status = EmojiStatus.LIKE
    )
    fun hate(req:EmojiRegisterReq, reqUserId: String) = Emoji (
        targetId = req.targetId,
        targetType = convertStatusNameToEnum(req.targetType),
        userId = reqUserId,
        status = EmojiStatus.HATE
    )


    private fun convertStatusNameToEnum(targetName:String): TargetType {
        return TargetType.fromString(targetName)
    }
}
