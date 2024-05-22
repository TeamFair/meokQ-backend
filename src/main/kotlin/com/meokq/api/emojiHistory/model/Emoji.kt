package com.meokq.api.emojiHistory.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.emojiHistory.enums.EmojiStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_emoji")
class Emoji(
    @Id
    @UuidGenerator
    var id : String? = null,
    @Enumerated(EnumType.STRING)
    var status : EmojiStatus? = null,
) : BaseModel()
