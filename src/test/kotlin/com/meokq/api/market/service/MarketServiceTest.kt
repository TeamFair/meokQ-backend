package com.meokq.api.market.service

import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.enums.TypeYN
import com.meokq.api.market.enums.WeekDay
import com.meokq.api.market.request.MarketTimeReq
import com.meokq.api.market.request.MarketUpdReq
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("local")
internal class MarketServiceTest {
    @Autowired
    private lateinit var marketService: MarketService

    @Test
    @Transactional
    fun updateMarket() {
        // given
        val authReq = AuthReq(
            userType = UserType.BOSS,
            userId = "sampleId",
        )

        val marketId = "MK00000001"
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
    }
}