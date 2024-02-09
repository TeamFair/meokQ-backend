package com.meokq.api.user.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.user.enums.AgreementType
import com.meokq.api.user.request.AgreementReq
import com.meokq.api.user.request.AgreementSearchDto
import com.meokq.api.user.response.AgreementResp
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class AgreementServiceTest {
    @Autowired
    private lateinit var service: AgreementService

    /**
     * sample Data
     */
    private val sampleAuthReq = AuthReq(
        userType = UserType.BOSS,
        userId = "CS10000001"
    )

    private val sampleSearchDto1 = AgreementSearchDto(
        userId = null,
        agreementType = AgreementType.PERSONAL_INFO_COLLECTION,
    )

    private val sampleSearchDto2 = AgreementSearchDto(
        userId = null,
        agreementType = AgreementType.PRIVACY_CONSENT_FORM_BOSS,
    )

    private val samplePageable = PageRequest.of(0, 10)

    private val agreementReq = AgreementReq(
        agreementType = AgreementType.PRIVACY_CONSENT_FORM_BOSS,
        version = 1,
        acceptYn = TypeYN.Y,
    )

    /**
     * call function
     */
    private fun findAll(searchDto: AgreementSearchDto): PageImpl<AgreementResp>{
        return service.findAll(
            authReq = sampleAuthReq,
            searchDto = searchDto,
            pageable = samplePageable,
        )
    }

    @Test
    fun findAll() {
        // given
        // when
        val respList = findAll(sampleSearchDto1)

        // then
        assert(!respList.isEmpty)
        Assertions.assertEquals(sampleSearchDto1.agreementType, respList.content.first().agreementType)
    }

    @Test
    @Transactional
    fun saveAll() {
        // given
        // when
        service.saveAll(
            authReq = sampleAuthReq,
            reqList = mutableListOf(agreementReq)
        )

        // then
        val respList = findAll(sampleSearchDto2)
        assert(!respList.isEmpty)
    }


}