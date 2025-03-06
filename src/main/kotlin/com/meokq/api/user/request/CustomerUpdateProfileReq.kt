package com.meokq.api.user.request

import jakarta.validation.constraints.NotBlank

class CustomerUpdateProfileReq(
    @field:NotBlank
    val imageId : String
)
