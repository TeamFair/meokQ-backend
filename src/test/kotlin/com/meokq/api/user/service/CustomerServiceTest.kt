package com.meokq.api.user.service

import com.meokq.api.auth.enums.AuthChannel
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.auth.request.LoginReq
import com.meokq.api.user.model.Customer
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
     * Sample Data
     */
    private val sampleAuthReq = AuthReq(
        userId = "CS10000001",
        userType = UserType.CUSTOMER,
    )

    private val sampleModel = Customer(
        customerId = "CS10000001",
        email = "user1@example.com",
        nickname = "nickname1",
    )

    /**
     * call function
     */
    @Test
    fun findByEmail() {
        // given
        val userId = sampleModel.customerId
        val email = sampleModel.email!!

        // when
        val resp = service.findByEmail(email)

        // then
        Assertions.assertEquals(userId, resp.customerId)
    }

    @Test
    fun findByAuthReq() {
        // given
        val nickname = "nickname1"

        // when
        val resp = service.findByAuthReq(sampleAuthReq)

        // then
        Assertions.assertEquals(nickname, resp.nickname)
    }

    @Test
    @Transactional
    fun save() {
        // given
        val req = LoginReq(
            email = UUID.randomUUID().toString(),
            channel = AuthChannel.APPLE,
            accessToken = "accessToken",
            refreshToken = null,
            userType = UserType.CUSTOMER,
        )

        // when
       val model = service.save(req)

        // then
        Assertions.assertEquals(req.email, model.email)
    }

    @Test
    @Transactional
    fun update() {
        // given
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