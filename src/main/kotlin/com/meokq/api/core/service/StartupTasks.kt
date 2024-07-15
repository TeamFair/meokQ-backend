package com.meokq.api.core.service

import com.meokq.api.challenge.service.ChallengeService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service

@Service
class StartupTasks(
    private val challengeService: ChallengeService
) :ApplicationRunner {
    override fun run(rgs: ApplicationArguments?) {
        challengeService.syncRank()
    }
}
