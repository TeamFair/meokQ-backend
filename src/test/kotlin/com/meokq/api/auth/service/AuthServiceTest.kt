package com.meokq.api.auth.service

import com.meokq.api.TestData
import com.meokq.api.TestData.loginReqAdmin
import com.meokq.api.TestData.loginReqBS10000001
import com.meokq.api.TestData.loginReqBossForSave
import com.meokq.api.TestData.loginReqCS10000001
import com.meokq.api.TestData.loginReqCustomerForSave
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.user.enums.UserStatus
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class AuthServiceTest{

    @Autowired
    private lateinit var service: AuthService
    @Autowired
    private lateinit var jwtTokenService: JwtTokenService
    @Autowired
    private lateinit var customerService: CustomerService
    @Autowired
    private lateinit var bossService: BossService

    /**
     * common
     */
    @Transactional
    fun loginOrRegister(loginReq : LoginReq) {
        // when
        val result = service.login(loginReq)

        // then
        Assertions.assertNotNull(result.authorization)
        val authReq = jwtTokenService.convertToRequest(result.authorization!!)
        Assertions.assertNotNull(authReq.userId)
        Assertions.assertEquals(authReq.userType, loginReq.userType)
    }

    @Transactional
    fun withdraw(authReq: AuthReq) {
        // given
        // when
        val result = service.withdraw(authReq)

        // then
        Assertions.assertEquals(UserStatus.DORMANT, result.status)
    }

    /**
     * login / register
     */
    @Test
    @DisplayName("customer 타입의 사용자가 로그인을 요청합니다.")
    fun loginOrRegisterCustomer() {
        loginOrRegister(loginReqCustomerForSave)
        loginOrRegister(loginReqCS10000001)
    }

    @Test
    @DisplayName("boss 타입의 사용자가 로그인을 요청합니다.")
    fun loginOrRegisterBoss() {
        loginOrRegister(loginReqBossForSave)
        loginOrRegister(loginReqBS10000001)
    }

    @Test
    @DisplayName("admin 타입의 사용자가 로그인을 요청합니다.")
    fun loginOrRegisterAdmin() {
        loginOrRegister(loginReqAdmin)
    }


    /**
     * withdraw
     */
    @Test
    @DisplayName("customer 타입의 사용자가 회원탈퇴을 요청합니다.")
    fun withdrawForCustomer() {
        val customer = TestData.saveCustomer(customerService)
        val authReq = AuthReq(
        userId = customer.customerId,
        userType = UserType.CUSTOMER,
        )
        withdraw(authReq)
    }

    @Test
    @DisplayName("boss 타입의 사용자가 회원탈퇴을 요청합니다.")
    fun withdrawForBoss() {
        val boss = TestData.saveBoss(bossService)
        val bossReq = AuthReq(
            userType = UserType.BOSS,
            userId = boss.bossId
        )
        withdraw(bossReq)
    }
}