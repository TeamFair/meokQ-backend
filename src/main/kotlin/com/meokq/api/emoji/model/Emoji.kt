package com.meokq.api.emoji.model

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.model.BaseModel
import com.meokq.api.emoji.enums.EmojiStatus
import com.meokq.api.emoji.enums.TargetType
import com.meokq.api.user.model.Customer
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_emoji")
class Emoji(
    @Id
    @UuidGenerator
    var id : String? = null,
    @Enumerated(EnumType.STRING)
    var status : EmojiStatus? = null,

    var userId : String? = null,

    @Enumerated(EnumType.STRING)
    var targetType : TargetType? = null,

    var targetId : String? = null
): BaseModel()
