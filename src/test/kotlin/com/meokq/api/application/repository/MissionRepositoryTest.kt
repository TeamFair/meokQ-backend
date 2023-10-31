package com.meokq.api.application.repository

import com.meokq.api.application.model.Mission
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.Arrays
import java.util.UUID

@DataJpaTest
internal class MissionRepositoryTest{
    @Autowired
    private lateinit var repository : MissionRepository

    @DisplayName("Mission 신규 등록 테스트")
    @Test
    fun saveMission() {
        // given
        val mission = Mission(
            missionId = UUID.randomUUID(),
            target = "coffee",
            quantity = 2,
            questId = "QS00001000"
        )

        // when
        val result = repository.save(mission)

        // then
        assertNotNull(result.missionId)
        assertSame(mission.target, result.target)
        assertSame(mission.quantity, result.quantity)
        assertSame(mission.questId, result.questId)
    }

    @DisplayName("Mission List 등록 테스트")
    @Test
    fun saveMissionList(){
        // given
        val questId = "QS00001000"
        val missions = listOf(
            Mission(
                missionId = UUID.randomUUID(),
                target = "coffee",
                quantity = 2,
                questId = questId),
            Mission(
                missionId = UUID.randomUUID(),
                target = "chocolate",
                quantity = 3,
                questId = questId)
        )

        // when
        val result = repository.saveAll(missions)

        // then
        assertSame(missions.size, result.size)
    }
}