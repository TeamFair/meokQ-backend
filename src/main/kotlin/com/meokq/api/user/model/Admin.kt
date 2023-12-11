package com.meokq.api.user.model

import com.meokq.api.core.model.BaseModel
import com.meokq.api.user.enums.UserStatus
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.hibernate.annotations.UuidGenerator

@Entity(name = "tb_admin")
data class Admin(
    @Id
    @UuidGenerator
    var adminId : String? = null,
    @Enumerated(EnumType.STRING)
    var status : UserStatus = UserStatus.ACTIVE,
) : BaseModel()
