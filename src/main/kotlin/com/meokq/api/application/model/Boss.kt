package com.meokq.api.application.model

import com.meokq.api.application.enums.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull
import java.time.LocalDateTime

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
    @CreationTimestamp
    var createDate : LocalDateTime? = null,
    @UpdateTimestamp
    var updateDate : LocalDateTime? = null,
)
