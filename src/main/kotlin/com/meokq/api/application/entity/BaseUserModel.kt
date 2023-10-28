package com.meokq.api.application.entity

import com.meokq.api.application.enums.UserStatus
import jakarta.persistence.MappedSuperclass
import org.jetbrains.annotations.NotNull

@MappedSuperclass
open class BaseUserModel : BaseModel() {
    @NotNull
    var status : UserStatus? = UserStatus.ACTIVE

    var name : String? = null
    var nickname : String? = null
}