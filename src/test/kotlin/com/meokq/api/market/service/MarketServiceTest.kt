package com.meokq.api.market.service

import com.meokq.api.TestData
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.market.enums.WeekDay
import com.meokq.api.market.request.MarketTimeReq
import com.meokq.api.market.request.MarketUpdReq
import com.meokq.api.user.service.BossService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
@Transactional
internal class MarketServiceTest {
    @Autowired
    private lateinit var marketService: MarketService
    @Autowired
    private lateinit var marketTimeService: MarketTimeService
    @Autowired
    private lateinit var bossService: BossService

    @Test
    fun updateMarket() {
        // given
        val testBoss = TestData.saveBoss(bossService)
        val testMarket = TestData.saveMarket(marketService, testBoss)
        val authReq = AuthReq(
            userType = UserType.BOSS,
            userId = testBoss.bossId,
        )

        val marketId = testMarket.marketId!!
        val request = MarketUpdReq(
            logoImageId = null,
            address = "address-sample",
            marketTime = mutableListOf(
                MarketTimeReq(
                    weekDay = WeekDay.MON,
                    openTime = "09:00",
                    closeTime = "13:00",
                    holidayYn = TypeYN.N
                )
            ),
            phone = "0211111110",
        )

        // when
        marketService.updateMarket(
            authReq = authReq,
            request =  request,
            marketId = marketId,
        )

        // then
        val market = marketService.findModelById(marketId)
        Assertions.assertEquals(request.phone, market.phone)
        Assertions.assertEquals(request.address, market.address)

        // 새로 등록한 영업시간 정보만 조회되어야 함.
        val marketTimes = marketTimeService.findAllByMarketId(marketId)
        Assertions.assertEquals(request.marketTime.size, marketTimes.size)
    }
}