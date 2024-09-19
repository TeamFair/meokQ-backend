package com.meokq.api.user.request

import jakarta.validation.constraints.NotBlank

class CustomerUpdateReq(
    @field:NotBlank
    val nickname : String
)