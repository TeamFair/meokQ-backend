package com.meokq.api.user.service

import com.meokq.api.TestData
import com.meokq.api.TestData.loginReqCustomerForSave
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.exception.NotUniqueException
import com.meokq.api.user.request.CustomerUpdateReq
import com.meokq.api.user.request.RankSearchCondition
import com.meokq.api.xp.model.XpType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
internal class CustomerServiceTest {
    @Autowired
    private lateinit var service: CustomerService

    /**
     * call function
     */
    @Test
    fun findByEmail() {
        // given
        val customer = TestData.saveCustomer(service)
        val userId = customer.customerId
        val email = customer.email!!

        // when
        val resp = service.findByEmail(email)

        // then
        Assertions.assertEquals(userId, resp.userId)
    }

    @Test
    fun findByAuthReq() {
        // given
        val customer = TestData.saveCustomer(service)
        val authReq = AuthReq(
            userId = customer.customerId,
            userType = UserType.CUSTOMER
        )

        // when
        val resp = service.findByAuthReq(authReq)

        // then
        Assertions.assertEquals(customer.nickname, resp.nickname)
    }

    @Test
    @Transactional
    fun registerMember() {
        // given
        val req = loginReqCustomerForSave
        loginReqCustomerForSave.userId = UUID.randomUUID().toString()
        loginReqCustomerForSave.email = UUID.randomUUID().toString()

        // when
       val result = service.registerMember(req)

        // then
        requireNotNull(result.userId)
        val model = service.findModelById(result.userId!!)
        Assertions.assertEquals(req.email, model.email)
    }

    @Test
    @Transactional
    @DisplayName("이메일이 같은 사용자는 등록할 수 없습니다.")
    fun registerMember2() {
        // given
        val req = loginReqCustomerForSave
        loginReqCustomerForSave.userId = UUID.randomUUID().toString()
        loginReqCustomerForSave.email = loginReqCustomerForSave.userId+"@example.com"

        // when
        service.registerMember(req)
        Thread.sleep(100)

        Assertions.assertThrows(NotUniqueException::class.java){
            service.registerMember(req)
        }
    }

    @Test
    @Transactional
    fun update() {
        // given
        val customer = TestData.saveCustomer(service)
        val sampleAuthReq = AuthReq(
            userId = customer.customerId,
            userType = UserType.CUSTOMER
        )
        val request = CustomerUpdateReq(
            nickname = UUID.randomUUID().toString()
        )

        // when
        service.update(
            authReq = sampleAuthReq,
            request = request,
        )

        // then
        Assertions.assertNotNull(sampleAuthReq.userId)
        val model = service.findModelById(sampleAuthReq.userId!!)
        Assertions.assertEquals(request.nickname, model.nickname)
        Assertions.assertEquals(sampleAuthReq.userId, model.customerId)
    }


    @Test
    @Transactional
    @DisplayName("XP랭크를 조회하면 XP 유저 순위가 조회 되어야 한다.")
    fun getRank() {
        // given
        val req = RankSearchCondition(
            xpType = XpType.STRENGTH,
            size = 5
        )
        // when
        val result = service.getRankForXp(req)

        // then
        Assertions.assertEquals(5, result.size)
       /* result.forEach {
            Assertions.assertNotNull(it.customerId)
            Assertions.assertNotNull(it.nickname)
        }*/

    }
}