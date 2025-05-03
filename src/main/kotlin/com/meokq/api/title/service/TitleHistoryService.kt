package com.meokq.api.title.service

import com.meokq.api.auth.request.AuthReq
import com.meokq.api.core.JpaService
import com.meokq.api.quest.model.Reward
import com.meokq.api.title.model.TitleHistory
import com.meokq.api.title.repository.TitleHistoryRepository
import com.meokq.api.title.response.TitleHistoryRankResp
import com.meokq.api.title.response.TitleHistoryResp
import com.meokq.api.user.model.Customer
import com.meokq.api.xp.model.XpType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class TitleHistoryService(
    val repository: TitleHistoryRepository,
): JpaService<TitleHistory, String> {
    override var jpaRepository: JpaRepository<TitleHistory, String> = repository

    @Transactional
    fun createBySignUp(customer: Customer) {
        customer.addTitle("TQ00001")
    }

    @Transactional
    fun createByChallengeFirst(customer: Customer) {
        customer.addTitle("TQ00002")
    }

    @Transactional
    fun createByReward(reward: Reward, customer: Customer) {
        customer.addTitle(reward.content!!)
    }

    @Transactional
    fun createByXp(customer: Customer) {

        customer.xp.forEach {
            when(it.xpType) {
                XpType.STRENGTH -> {
                    if (it.xpPoint >= 50000) customer.addTitle("TQ00007")
                    if (it.xpPoint >= 10000) customer.addTitle("TQ00006")
                    if (it.xpPoint >= 5000) customer.addTitle("TQ00005")
                    if (it.xpPoint >= 1000) customer.addTitle("TQ00004")
                    if (it.xpPoint >= 100) customer.addTitle("TQ00003")
                }
                XpType.CHARM -> {
                    if (it.xpPoint >= 50000) customer.addTitle("TQ00012")
                    if (it.xpPoint >= 10000) customer.addTitle("TQ00011")
                    if (it.xpPoint >= 5000) customer.addTitle("TQ00010")
                    if (it.xpPoint >= 1000) customer.addTitle("TQ00009")
                    if (it.xpPoint >= 100) customer.addTitle("TQ00008")
                }
                XpType.SOCIABILITY -> {
                    if (it.xpPoint >= 50000) customer.addTitle("TQ00017")
                    if (it.xpPoint >= 10000) customer.addTitle("TQ00016")
                    if (it.xpPoint >= 5000) customer.addTitle("TQ00015")
                    if (it.xpPoint >= 1000) customer.addTitle("TQ00014")
                    if (it.xpPoint >= 100) customer.addTitle("TQ00013")
                }
                XpType.FUN -> {
                    if (it.xpPoint >= 50000) customer.addTitle("TQ00022")
                    if (it.xpPoint >= 10000) customer.addTitle("TQ00021")
                    if (it.xpPoint >= 5000) customer.addTitle("TQ00020")
                    if (it.xpPoint >= 1000) customer.addTitle("TQ00019")
                    if (it.xpPoint >= 100) customer.addTitle("TQ00018")
                }
                XpType.INTELLECT -> {
                    if (it.xpPoint >= 50000) customer.addTitle("TQ00027")
                    if (it.xpPoint >= 10000) customer.addTitle("TQ00026")
                    if (it.xpPoint >= 5000) customer.addTitle("TQ00025")
                    if (it.xpPoint >= 1000) customer.addTitle("TQ00024")
                    if (it.xpPoint >= 100) customer.addTitle("TQ00023")
                }
                null -> {}
            }
        }
    }

    @Transactional
    fun updateRead(titleHistoryId: String) {
        val titleHistory = this.findModelById(titleHistoryId)
        titleHistory.readYn = true
    }

    fun findAllForUnRead(authReq: AuthReq): List<TitleHistoryResp> {
        return this.repository.findAllForUnRead(authReq.userId!!)
    }

    fun findAllByCustomerId(authReq: AuthReq): List<TitleHistoryResp> {
        return this.repository.findAllByCustomerId(authReq.userId!!)
    }

    fun findAllByRank(titleId: String): List<TitleHistoryRankResp> {
        return this.repository.findAllByRank(titleId)
    }

}
