package com.meokq.api.test.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.meokq.api.test.dto.TestDto
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@AutoConfigureMockMvc
@SpringBootTest
internal class TestControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private val basePath = "/test"

    @Test
    fun get() {
        // Given
        val url = "$basePath/get"

        // When
        val resultActions = mockMvc.perform(
            get(url)
                .contentType(MediaType.APPLICATION_JSON)
        )

        // Then
        resultActions
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message", equalTo("hello")))
            .andDo(print())
    }

    @Test
    fun post() {
        // Given
        val url = "$basePath/post"
        val testDto = TestDto("param1", "param2", "param3")

        // When
        val resultActions = mockMvc.perform(
            post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testDto))
        )

        // Then
        resultActions
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.param1", equalTo(testDto.param1)))
            .andExpect(jsonPath("$.param2", equalTo(testDto.param2)))
            .andExpect(jsonPath("$.param3", equalTo(testDto.param3)))
            .andDo(print())
    }
}