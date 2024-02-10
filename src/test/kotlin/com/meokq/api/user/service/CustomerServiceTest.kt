package com.meokq.api.user.service

import com.meokq.api.TestData.authReqCS10000001
import com.meokq.api.TestData.customerCS10000001
import com.meokq.api.TestData.loginReqCustomerForSave
import com.meokq.api.user.request.CustomerUpdateReq
import org.junit.jupiter.api.Assertions
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
        val customer = customerCS10000001
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
        val authReq = authReqCS10000001
        val customer = customerCS10000001

        // when
        val resp = service.findByAuthReq(authReq)

        // then
        Assertions.assertEquals(customer.nickname, resp.nickname)
    }

    @Test
    @Transactional
    fun save() {
        // given
        val req = loginReqCustomerForSave

        // when
       val result = service.registerMember(req)

        // then
        requireNotNull(result.userId)
        val model = service.findModelById(result.userId!!)
        Assertions.assertEquals(req.email, model.email)
    }

    @Test
    @Transactional
    fun update() {
        // given
        val sampleAuthReq = authReqCS10000001
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
}