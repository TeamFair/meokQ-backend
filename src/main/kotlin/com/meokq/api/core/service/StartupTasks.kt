package com.meokq.api.core.service

import com.meokq.api.challenge.service.ChallengeService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service

@Service
@Slf4j
class StartupTasks(
    private val challengeService: ChallengeService
) :ApplicationRunner {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(StartupTasks::class.java)
    }

    override fun run(rgs: ApplicationArguments?) {
        challengeService.syncRank()
        logger.info("StartupTasks.run")
    }
}
