package com.meokq.api.user.model

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

@Entity(name = "tb_customer")
data class Customer(
    @Id
    @UuidGenerator
    var customerId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : UserStatus = UserStatus.ACTIVE,
    @NotNull
    @Column(unique = true)
    var email : String? = null,
    var nicknameSeq: Long? = null,
    var nickname : String? = null,
    @Enumerated(EnumType.STRING)
    var channel: AuthChannel? = null,
    @Column(name = "withdrawn_at")
    var withdrawnAt : LocalDateTime? = null,
    @Column(name = "xp_point")
    var xpPoint : Long? = 0
) : BaseModel() {
    constructor(request : LoginReq) : this(
        email = request.email,
        channel = request.channel,
    )
    fun gainXp(xp: Long) {
        xpPoint = xpPoint?.plus(xp)
    }

}
