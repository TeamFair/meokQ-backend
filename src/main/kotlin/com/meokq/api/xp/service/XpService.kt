package com.meokq.api.xp.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.exception.NotFoundException
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.model.Customer
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.dto.response.XpStatsResp
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.repository.XpRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class XpService(
    private val customerRepository: CustomerRepository,
    private val repository: XpRepository,
    private val xpHistoryService: XpHistoryService,
) : JpaService<Xp, String> {

    override var jpaRepository: JpaRepository<Xp, String> = repository

    fun gain(userAction: UserAction, metadata: TargetMetadata) {
        val customer = getCustomer(metadata.userId)
        val model = repository.findByCustomerAndXpType(customer, userAction.xpType!!)
            ?.apply { incrementXp(userAction.xpPoint) }
            ?: Xp(xpType = userAction.xpType, xpPoint = userAction.xpPoint).also {
                customer.addXp(it)
                customerRepository.save(customer)
            }

        saveModel(model)
        xpHistoryService.writeHistory(userAction, metadata.userId)
    }

    fun withdraw(userAction: UserAction, metadata: TargetMetadata) {
        val customer = getCustomer(metadata.userId)

        repository.findByCustomerAndXpType(customer, userAction.xpType!!)?.let {
            it.decrementXp(userAction.xpPoint)
            saveModel(it)
            customerRepository.save(customer)
        }
        xpHistoryService.withdrawHistory(userAction, metadata.userId)
    }

    private fun getCustomer(customerId: String) = customerRepository.findById(customerId)
        .orElseThrow { NotFoundException("유저를 찾을 수 없습니다. : ${customerId}") }


    @Transactional(readOnly = true)
    fun fetchStats(userId: String): XpStatsResp {
        val customer = getCustomer(userId)
        return repository.findByCustomer(customer).let {
            XpStatsResp(it)
        }
    }


}