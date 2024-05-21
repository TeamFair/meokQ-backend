package com.meokq.api.user.model

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.core.enums.DateTimePattern
import com.meokq.api.core.model.BaseModel
import com.meokq.api.emojiHistory.enums.EmojiStatus
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime
import kotlin.random.Random

@Entity(name = "tb_emoji")
data class Emoji(
    @Id
    @UuidGenerator
    var id : String? = null,
    @Enumerated(EnumType.STRING)
    var status : EmojiStatus? = null,
    var emojiHistoryId : String? = null,
) : BaseModel()
