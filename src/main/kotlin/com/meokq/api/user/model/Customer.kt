package com.meokq.api.user.model

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.core.converter.DateTimeConverterV2
import com.meokq.api.core.enums.DateTimePattern
import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.UserStatus
import jakarta.persistence.*
import org.hibernate.annotations.UuidGenerator
import org.jetbrains.annotations.NotNull
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

@Entity(name = "tb_customer")
data class Customer(
    @Id
    @UuidGenerator
    @Column(name = "customer_id")
    var customerId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : UserStatus = UserStatus.ACTIVE,
    @NotNull
    @Column(unique = true)
    var email : String? = null,
    var nickname : String? = null,
    @Enumerated(EnumType.STRING)
    var channel: AuthChannel? = null,
    @Column(name = "withdrawn_at")
    var withdrawnAt : LocalDateTime? = null
) : BaseModel() {
    constructor(request : LoginReq) : this(
        email = request.email,
        channel = request.channel,
    ) {
        nickname = "USER${DateTimeConverterV2.convertToString(LocalDateTime.now(), DateTimePattern.COMPACT)}${String.format("%02d", Random.nextInt(0, 101))}"
    }

}
