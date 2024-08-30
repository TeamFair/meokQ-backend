package com.meokq.api.xp.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.repository.XpRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class XpService(
    private val customerRepository : CustomerRepository,
    private val repository: XpRepository,
    private val xpHistoryService: XpHistoryService,
    ):JpaService<Xp,String> {

    override var jpaRepository: JpaRepository<Xp, String> = repository

    fun gain(userAction: UserAction, metadata: TargetMetadata) {
        val customer = customerRepository.findById(metadata.userId)
            .orElseThrow { NotFoundException("유저를 찾을 수 없습니다. : ${metadata.userId}") }

        val model = repository.findByCustomerAndXpType(customer, userAction.xpType!!)
            ?.apply { gain(userAction.xpPoint) }
            ?: Xp(xpType = userAction.xpType, xpPoint = userAction.xpPoint).also { customer.addXp(it) }

        saveModel(model)
        xpHistoryService.writeHistory(userAction, metadata.userId)
    }

    fun withdraw(userAction: UserAction, metadata: TargetMetadata) {
        val customer = customerRepository.findById(metadata.userId).orElseThrow()
        repository.findByCustomerAndXpType(customer, userAction.xpType!!)?.let {
            it.withdraw(userAction.xpPoint)
            saveModel(it)
        }
        xpHistoryService.withdrawHistory(userAction, metadata.userId)
    }


}