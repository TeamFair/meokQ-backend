package com.meokq.api.application.repository

import com.meokq.api.application.enums.MarketStatus
import com.meokq.api.application.model.Market
import com.meokq.api.application.request.MarketSearchDto
import com.meokq.api.application.specification.MarketSpecifications
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class MarketRepositoryTest {

    @Autowired
    lateinit var repository: MarketRepository

    @Test
    @Transactional
    fun save(){
        val model = Market(
            name = "name",
            presidentId = "presidentId",
            address = "address"
        )

        val result = repository.save(model)
        println(result)

        Assertions.assertSame(model.address, result.address)
        Assertions.assertSame(model.presidentId, result.presidentId)
    }

    @Test
    @DisplayName("동적쿼리 테스트1")
    @Transactional
    fun findMarketsByDistrictAndName() {
        // Arrange
        val market = Market(
            name = "Market Name",
            address = "Market Address",
            district = "Market District Test1888",
            content = "Market Content",
            phone = "Market Phone",
            ticketCount = 0,
            //logoImage = "Logo Image URL",
            status = MarketStatus.UNDER_REVIEW,
            presidentId = "President ID",
        )

        repository.save(market)

        // Act
        val searchDto = MarketSearchDto(
            district = market.district,
            presidentId = market.presidentId,
        )
        val dynamicSpecification: Specification<Market> = MarketSpecifications.bySearchDto(searchDto)
        val result = repository.findAll(dynamicSpecification, PageRequest.of(0, 10))

        // Assert
        assertEquals(1, result.totalElements)
        result.content[0].let {
            assertEquals(market.district, it.district)
            assertEquals(market.presidentId, it.presidentId)
            // Add more assertions as needed
        }
    }


    @Test
    @DisplayName("동적쿼리 테스트2")
    @Transactional
    fun findMarketsByDistrict() {
        // Arrange
        val market = Market(
            name = null,
            address = "Market Address",
            district = "Market District Test2",
            content = "Market Content",
            phone = "Market Phone",
            ticketCount = 0,
            //logoImage = "Logo Image URL",
            status = MarketStatus.UNDER_REVIEW,
            presidentId = "President ID",
        )

        repository.save(market)

        // Act
        val searchDto = MarketSearchDto(district = market.district)
        val dynamicSpecification: Specification<Market> = MarketSpecifications.bySearchDto(searchDto)
        val result = repository.findAll(dynamicSpecification, PageRequest.of(0, 10))

        // Assert
        assertEquals(1, result.totalElements)
        //assertEquals(market.district, result[0].district)
        // Add more assertions as needed
    }
}