package com.meokq.api.agreement.service

import com.meokq.api.TestData
import com.meokq.api.agreement.enums.AgreementType
import com.meokq.api.agreement.request.AgreementReq
import com.meokq.api.agreement.request.AgreementSearchDto
import com.meokq.api.agreement.response.AgreementResp
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.user.service.BossService
import com.meokq.api.user.service.CustomerService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class AgreementServiceTest {
    @Autowired
    private lateinit var service: AgreementService
    @Autowired
    private lateinit var bossService: BossService
    @Autowired
    private lateinit var customerService: CustomerService

    /**
     * sample Data
     */
    private val authReqForBoss = AuthReq(
        userType = UserType.BOSS,
        userId = "BS10000001"
    )

    private val authReqForCustomer = AuthReq(
        userType = UserType.CUSTOMER,
        userId = "CS10000001"
    )

    private val sampleSearchDto1 = AgreementSearchDto(
        userId = null,
        agreementType = AgreementType.TERMS_OF_SERVICE_BOSS,
    )

    private val sampleSearchDto2 = AgreementSearchDto(
        userId = null,
        agreementType = AgreementType.PRIVACY_CONSENT_FORM_BOSS,
    )

    private val samplePageable = PageRequest.of(0, 10)

    private val agreementReqForBoss = AgreementReq(
        agreementType = AgreementType.PRIVACY_CONSENT_FORM_BOSS,
        version = 1,
        acceptYn = TypeYN.Y,
    )

    private val agreementReqForCustomer = AgreementReq(
        agreementType = AgreementType.PRIVACY_CONSENT_FORM_CUSTOMER,
        version = 1,
        acceptYn = TypeYN.Y,
    )

    /**
     * call function
     */
    private fun findAll(searchDto: AgreementSearchDto): PageImpl<AgreementResp>{
        return service.findAll(
            authReq = authReqForBoss,
            searchDto = searchDto,
            pageable = samplePageable,
        )
    }

    @Test
    fun findAll() {
        // given
        val boss = TestData.saveBoss(bossService)
        val result = service.saveAll(
            authReq = AuthReq(
                userId = boss.bossId,
                userType = UserType.BOSS
            ),
            reqList = mutableListOf(
                AgreementReq(
                    agreementType = AgreementType.PRIVACY_CONSENT_FORM_BOSS,
                    version = 1,
                    acceptYn = TypeYN.Y,
                ),
                AgreementReq(
                    agreementType = AgreementType.TERMS_OF_SERVICE_BOSS,
                    version = 1,
                    acceptYn = TypeYN.Y,
                ),
            )
        )

        // when
        Thread.sleep(1000)
        val respList = findAll(
            AgreementSearchDto(
                userId = boss.bossId,
                agreementType = AgreementType.TERMS_OF_SERVICE_BOSS,
            )
        )

        // then
        Assertions.assertEquals(1, respList.content.size)
        Assertions.assertEquals(
            AgreementType.TERMS_OF_SERVICE_BOSS
            , respList.content.first().agreementType
        )
    }

    @Test
    fun saveAll() {
        // when
        service.saveAll(
            authReq = authReqForBoss,
            reqList = mutableListOf(agreementReqForBoss)
        )

        // then
        val respList = findAll(sampleSearchDto2)
        assert(!respList.isEmpty)
    }

    @Test
    @Transactional
    @DisplayName("BOSS 타입의 사용자는 _BOSS 로 끝나는 약관만 사용가능합니다.")
    fun saveAllForBoss() {
        // given
        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            service.saveAll(
                authReq = authReqForBoss,
                reqList = mutableListOf(agreementReqForCustomer)
            )
        }

        Assertions.assertDoesNotThrow {
            service.saveAll(
                authReq = authReqForBoss,
                reqList = mutableListOf(agreementReqForBoss)
            )
        }
    }

    @Test
    @Transactional
    @DisplayName("CUSTOMER 타입의 사용자는 _CUSTOMER 로 끝나는 약관만 사용가능합니다.")
    fun saveAllForCustomer() {
        // given
        // when
        Assertions.assertThrows(InvalidRequestException::class.java){
            service.saveAll(
                authReq = authReqForCustomer,
                reqList = mutableListOf(agreementReqForBoss)
            )
        }

        Assertions.assertDoesNotThrow {
            service.saveAll(
                authReq = authReqForCustomer,
                reqList = mutableListOf(agreementReqForCustomer)
            )
        }
    }


}