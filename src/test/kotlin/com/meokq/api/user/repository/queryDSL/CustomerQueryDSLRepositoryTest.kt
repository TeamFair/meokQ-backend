package com.meokq.api.user.repository.queryDSL

import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.model.XpType
import com.meokq.api.xp.repository.XpRepository
import org.assertj.core.api.Assertions.tuple
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.assertj.core.groups.Tuple
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import org.hibernate.internal.util.collections.CollectionHelper.listOf
import java.util.UUID

@Transactional
@ActiveProfiles("local")
@SpringBootTest
internal class CustomerQueryDSLRepositoryTest{

    @Autowired
    private lateinit var customerQueryDSLRepository: CustomerQueryDSLRepository

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @Autowired
    private lateinit var xpRepository: XpRepository

    @BeforeEach
    fun setUp() {
        customerRepository.deleteAll()
        xpRepository.deleteAll()
    }

    @DisplayName("xp 합을 기준으로 top 사용자 리스트를 조회한다.")
    @Test
    fun getTopUsersByXp() {
        // given
        // > save customer1
        val customer1 = Customer(email = "email-01", nickname = "nickname-01")
        val saveCustomer1 = customerRepository.save(customer1)

        val xp01 = Xp(xpPoint = 10L, xpType = XpType.SOCIABILITY, customer = saveCustomer1)
        val xp02 = Xp(xpPoint = 20L, xpType = XpType.STRENGTH, customer = saveCustomer1)
        xpRepository.saveAll(listOf(xp01, xp02))

        // > save customer2
        val customer2 = Customer(email = "email-02", nickname = "nickname-02")
        val saveCustomer2 = customerRepository.save(customer2)

        val xp03 = Xp(xpPoint = 5L, xpType = XpType.CHARM, customer = saveCustomer2)
        xpRepository.saveAll(listOf(xp03))

        // when
        val resps = customerQueryDSLRepository.getTopUsersByXp(10)

        // then
        assertThat(resps)
            .isNotEmpty
            .hasSize(2)
            .extracting("nickname", "xpSum", "lank")
            .containsExactly(
                tuple("nickname-01", 30L, 1),
                tuple("nickname-02", 5L, 2),
            )
    }
}