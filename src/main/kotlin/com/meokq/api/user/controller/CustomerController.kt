package com.meokq.api.user.controller

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.AuthDataProvider
import com.meokq.api.core.ResponseEntityCreation
import com.meokq.api.core.dto.BaseListRespV2
import com.meokq.api.core.dto.BaseResp
import com.meokq.api.user.annotaions.*
import com.meokq.api.user.request.CustomerUpdateProfileReq
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.user.response.CustomerResp
import com.meokq.api.user.service.CustomerService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "사용자 정보")
@Controller
@RequestMapping("/api")
class CustomerController(
    private val service: CustomerService
): ResponseEntityCreation, AuthDataProvider {
    @ExplainSelectCustomer
    @GetMapping("/customer/user")
    fun findById(@RequestParam customerId: String?): ResponseEntity<BaseResp> {
        val request = if (customerId.isNullOrBlank()) {
            getAuthReq()
        } else {
            AuthReq(UserType.CUSTOMER, customerId)
        }
        return getRespEntity(service.findByAuthReq(request))
    }

    @ExplainUpdateCustomer
    @PutMapping("/customer/user")
    fun update(@Valid @RequestBody request : CustomerUpdateReq): ResponseEntity<BaseResp> {
        return getRespEntity(service.update(
            authReq = getAuthReq(),
            request = request
        ))
    }

    @ExplainDeleteCustomerProfileImage
    @DeleteMapping("/customer/user/image")
    fun deleteProfileImage(): ResponseEntity<BaseResp> {
        return getRespEntity(service.deleteProfileImage(
            authReq = getAuthReq(),
        ))
    }

    @ExplainUpdateCustomerProfileImage
    @PutMapping("/customer/user/image")
    fun updateProfileImage(@Valid @RequestBody request: CustomerUpdateProfileReq): ResponseEntity<BaseResp> {
        return getRespEntity(service.updateProfileImage(
            authReq = getAuthReq(),
            request = request,
        ))
    }

    @ExplainGetRankForXpCustomer
    @GetMapping("/customer/rank")
    fun getRankForXp(request : RankSearchCondition): ResponseEntity<BaseResp> {
        return getRespEntity(service.getRankForXp(
            request
        ))
    }

    @ExplainSelectTopUserByXp
    @GetMapping("/open/v1/rank/top-users")
    fun getTopUsersByXp(
        @RequestParam(defaultValue = "10") limit: Long,
    ) : ResponseEntity<BaseListRespV2> {
        return getListRespEntity(service.getTopUsersByXp(limit))
    }

    @ExplainUpdateCustomerTitle
    @PutMapping("/customer/user/title")
    fun updateTitle(
        @RequestParam titleHistoryId: String,
    ) : ResponseEntity<BaseResp> {
        return getRespEntity(service.updateTitle(getAuthReq(), titleHistoryId))
    }
}
