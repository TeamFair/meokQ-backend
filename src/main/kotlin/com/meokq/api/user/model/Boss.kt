package com.meokq.api.user.model

import com.meokq.api.user.enums.UserStatus
import com.meokq.api.core.model.BaseModel
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull

@Entity(name = "tb_boss")
data class Boss(
    @Id
    @UuidGenerator
    var bossId : String? = null,
    var nickName : String? = null,
    @Enumerated(EnumType.STRING)
    var status : UserStatus = UserStatus.ACTIVE,
    @NotNull
    @Column(unique = true)
    var email : String? = null,
) : BaseModel()
