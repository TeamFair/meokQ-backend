package com.meokq.api.market.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.market.reposone.MarketCreateResp
import com.meokq.api.market.request.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional
@SpringBootTest
@ActiveProfiles("local")
internal class MarketAuthServiceTest {

    @Autowired
    private lateinit var marketAuthService: MarketAuthService

    @Autowired
    private lateinit var marketService: MarketService

    private val marketReq = MarketReq(
        logoImageId = "testImageId",
        name = "test-market",
        phone = "0211112222",
        district = "distinct",
        address = "address",
        marketTime = listOf()
    )

    private var marketCreateResp: MarketCreateResp? = null

    private val authReq = AuthReq(
        userType = UserType.BOSS,
        userId = "test-id"
    )

    @BeforeEach
    fun initData(){
        marketCreateResp = marketService.saveMarket(
            request = marketReq,
            authReq = authReq
        )
    }

    @Test
    @DisplayName("마켓인증정보가 정상적으로 등록됩니다.")
    fun save() {
        // given
        val request = MarketAuthReq(
            marketId = marketCreateResp?.marketId,
            owner = OperatorAuthReq(
                name = "test",
                birthdate = LocalDate.now(),
                idcardImageId = "test-image"
            ),
            license = LicenseAuthReq(
                licenseId = "test-id",
                licenseImageId = "test-image",
                ownerName = "owner",
                marketName = "market-name",
                address = "address",
                postalCode = "0000",
                salesType = "salesType"
            )
        )

        // when
        val result = marketAuthService.save(request)

        // then
        requireNotNull(result.recordId)
        val findAll = marketAuthService.findAll(
            searchDto = MarketAuthSearchDto(marketId = request.marketId),
            pageable = Pageable.ofSize(100)
        ).content.toList().first()

        Assertions.assertEquals(request.license.licenseId, findAll?.license?.licenseId)
        Assertions.assertEquals(request.license.marketName, findAll?.license?.marketName)
        Assertions.assertEquals(request.license.salesType, findAll?.license?.salesType)
        Assertions.assertEquals(request.owner.name, findAll?.operator?.name)
        Assertions.assertEquals(request.owner.birthdate, findAll?.operator?.birthdate)
    }

    @Test
    fun submitReview() {
        // given
        // when
        // then
    }

    @Test
    fun requestReview() {
        // given
        // when
        // then
    }
}