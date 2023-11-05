package com.meokq.api.application.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.meokq.api.application.enums.UserType
import com.meokq.api.application.request.NoticeRequest
import com.meokq.api.application.request.NoticeSearchDto
import com.meokq.api.application.response.NoticeResponse
import com.meokq.api.application.service.NoticeService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

@WebMvcTest(controllers = [NoticeController::class])
internal class NoticeControllerTest {

    @InjectMocks
    private lateinit var noticeController: NoticeController

    @MockBean
    private lateinit var service: NoticeService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup(){
        MockitoAnnotations.openMocks(this)
        mockMvc = standaloneSetup(noticeController).build()
    }

    private fun getSampleData() = listOf(
        NoticeResponse(
            noticeId = "NT00000001",
            title = "sample-title1",
            content = "this is content1",
            createDate = "2023-12-03 12:00",
            target = UserType.CUSTOMER
        ),
        NoticeResponse(
            noticeId = "NT00000002",
            title = "sample-title2",
            content = "this is content2",
            createDate = "2020-12-03 12:00",
            target = UserType.MANAGER
        )
    )

    @Test
    @DisplayName("고객용 공지사항목록 조회")
    fun findAllForCustomer() {
        val pageRequest = PageRequest.of(0, 10)
        val searchDto = NoticeSearchDto(target = UserType.CUSTOMER, pageable = pageRequest)
        val response = getSampleData().filter { it.target == UserType.CUSTOMER }
        `when`(service.findAll(searchDto)).thenReturn(PageImpl<NoticeResponse>(response))

        mockMvc.perform(
            get("/notices")
                .param("target", UserType.CUSTOMER.name)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }

    @Test
    @DisplayName("매니저용 공지사항목록 조회")
    fun findAllForManager() {
        val pageRequest = PageRequest.of(0, 10)
        val searchDto = NoticeSearchDto(target = UserType.CUSTOMER, pageable = pageRequest)
        val response = getSampleData().filter { it.target == UserType.MANAGER }
        `when`(service.findAll(searchDto)).thenReturn(PageImpl<NoticeResponse>(response))

        mockMvc.perform(
            get("/notices")
                .param("target", UserType.CUSTOMER.name)
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andDo(print())
    }

    @Test
    @DisplayName("공지사항 등록")
    fun save() {
        val response = getSampleData().first()
        val request = NoticeRequest(
            title = response.title!!,
            content = response.content!!,
            target = response.target!!)
        `when`(service.save(request)).thenReturn(response)

        mockMvc.perform(
            post("/notices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated)
        .andDo(print())
    }
}