package com.meokq.api.application.repository

import com.meokq.api.application.model.Market
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MarketRepositoryTest {

    @Autowired
    lateinit var repository: MarketRepository

    @Test
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
}