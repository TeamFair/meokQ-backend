package com.meokq.api.emoji.model

import com.meokq.api.challenge.model.Challenge
import com.meokq.api.core.model.BaseModel
import com.meokq.api.emoji.enums.EmojiStatus
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    var customer : Customer? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    var challenge :Challenge? = null,
) : BaseModel()
