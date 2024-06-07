package com.meokq.api.emoji.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.emoji.request.EmojiRegisterReq
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_emoji")
class Emoji(
    @Id
    @UuidGenerator
    var emojiId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : EmojiStatus? = null,

    var userId : String? = null,

    @Enumerated(EnumType.STRING)
    var targetType : TargetType? = null,

    var targetId : String? = null
): BaseModel()
{
    fun appendTarget(req:EmojiRegisterReq, reqUserId: String){
        targetId = req.targetId
        targetType = convertStatusNameToEnum(req.targetType)
        userId = reqUserId

    }

    fun convertStatusNameToEnum(targetName:String): TargetType? {
        return TargetType.fromString(targetName)
    }
}
