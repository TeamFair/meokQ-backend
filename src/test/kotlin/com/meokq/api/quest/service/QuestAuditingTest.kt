package com.meokq.api.quest.service

import com.meokq.api.TestData
import com.meokq.api.TestData.questCreateReqForAdmin
import com.meokq.api.TestData.testFile
import com.meokq.api.auth.enums.UserType
import com.meokq.api.auth.filters.JwtFilter
import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.exception.InvalidRequestException
import com.meokq.api.file.enums.ImageType
import com.meokq.api.file.request.ImageReq
import com.meokq.api.quest.enums.QuestStatus
import com.meokq.api.quest.request.QuestCreateReq
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
@ActiveProfiles("local")
@Transactional
class QuestAuditingTest {
    @Value("\${matq.admin.id:admin}")
    private lateinit var adminId: String

    @Autowired
    private lateinit var jwtFilter: JwtFilter

    @Autowired
    private lateinit var service: QuestService

    @Test
    @DisplayName("등록자를 저장한다.(등록자를 찾을 수 없는 경우 UNKNOWN)쿼스트 등록이 되면 안된다.")
    fun saveCreatedBy(){
        // given
        val authReq = AuthReq(userType = UserType.UNKNOWN)
        jwtFilter.setSecurityContext(authReq)
        val req = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1)
        )

        // when
        // then
        Assertions.assertThrows(InvalidRequestException::class.java) {
            service.save(req, testFile, authReq)
        }

    }

    @Test
    @DisplayName("등록자를 저장한다. (등록자 아이디가 존재하는 경우)")
    fun saveCreatedBy1(){
        // given
        val authReq = AuthReq(userType = UserType.CUSTOMER, userId = UUID.randomUUID().toString())
        val req = QuestCreateReq(
            marketId = "MK00000001",
            missions = listOf(TestData.missionReqForSave1, TestData.missionReqForSave2),
            rewards = listOf(TestData.rewardReqForSave1)
        )
        jwtFilter.setSecurityContext(authReq)

        // when
        val result = service.save(req, testFile, authReq)
        val searchData = service.findModelById(result.questId!!)

        // then
        Assertions.assertNotNull(result.questId)
        Assertions.assertEquals(authReq.userId, searchData.createdBy)
    }

    @Test
    @DisplayName("등록자를 저장한다. (등록자 아이디가 존재하는 경우, 관리자용 저장함수를 사용하는 경우)")
    fun saveCreatedBy2(){
        // given
        val authReq = AuthReq(userType = UserType.ADMIN, userId = adminId)

        jwtFilter.setSecurityContext(authReq)

        // when
        val result = service.adminSave(questCreateReqForAdmin, ImageReq(ImageType.QUEST_IMAGE, TestData.testFile) , authReq)
        val searchData = service.findModelById(result.questId!!)

        // then
        Assertions.assertNotNull(result.questId)
        Assertions.assertEquals(authReq.userId, searchData.createdBy)
    }

    @Test
    @DisplayName("저장된 quest를 수정하는 경우, quest의 createdBy는 변하지 않아야 함.")
    fun saveCreatedBy3(){
        // given
        val authReq = AuthReq(userType = UserType.ADMIN, userId = adminId)
        val authReqForUpdate = AuthReq(userType = UserType.CUSTOMER, userId = "customer")

        jwtFilter.setSecurityContext(authReq)

        // when
        val result = service.adminSave(questCreateReqForAdmin, ImageReq(ImageType.QUEST_IMAGE, TestData.testFile) , authReq)
        val searchData = service.findModelById(result.questId!!)

        jwtFilter.setSecurityContext(authReqForUpdate)
        val updateResp = service.saveModel(searchData.also { it.status = QuestStatus.DELETED })

        // then
        Assertions.assertNotNull(result.questId)
        Assertions.assertEquals(authReq.userId, searchData.createdBy)
        Assertions.assertEquals(searchData.createdBy, updateResp.createdBy)
    }
}