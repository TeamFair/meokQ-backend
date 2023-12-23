package com.meokq.api.user.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull

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
    // TODO : 자동채번
    @UuidGenerator
    var nickname : String? = null,
) : BaseModel()
