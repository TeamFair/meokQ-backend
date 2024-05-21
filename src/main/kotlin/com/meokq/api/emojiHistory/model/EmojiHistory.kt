package com.meokq.api.user.model

import com.meokq.api.core.model.BaseModel
import jakarta.persistence.Entity
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_emojiHistory")
data class EmojiHistory(
    @Id
    @UuidGenerator
    var emojiHistoryId : String? = null,
    var customerId : String? = null,
    var emojiId : String? = null,
    var questId :String? = null,
) : BaseModel()
