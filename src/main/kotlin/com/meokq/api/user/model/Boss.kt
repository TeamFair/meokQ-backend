package com.meokq.api.user.model

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull

@Entity(name = "tb_boss")
data class Boss(
    @Id
    @UuidGenerator
    var bossId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : UserStatus = UserStatus.ACTIVE,
    @NotNull
    @Column(unique = true)
    var email : String? = null,
    @Enumerated(EnumType.STRING)
    var channel: AuthChannel? = null,
) : BaseModel(){
    constructor(request : LoginReq) : this(
        email = request.email,
        channel = request.channel
    )
}
