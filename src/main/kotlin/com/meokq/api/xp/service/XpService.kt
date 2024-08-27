package com.meokq.api.xp.service

import com.meokq.api.core.JpaService
import com.meokq.api.core.model.TargetMetadata
import com.meokq.api.user.repository.CustomerRepository
import com.meokq.api.xp.model.Xp
import com.meokq.api.xp.processor.UserAction
import com.meokq.api.xp.repository.XpRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

@Service
class XpService(
    private val customerRepository : CustomerRepository,
    private val repository: XpRepository,
    private val xpHistoryService: XpHistoryService,

    ):JpaService<Xp,String> {

    override var jpaRepository: JpaRepository<Xp, String> = repository

    fun register(userAction: UserAction, metadata: TargetMetadata){
        val customer = customerRepository.findById(metadata.userId).orElseThrow()
        val model = Xp(
            xpType = userAction.xpType,
            xpPoint = userAction.xpPoint)

        customer.addXp(model)
        saveModel(model)

        xpHistoryService.save(userAction, metadata)
    }

    fun delete(userAction: UserAction, metadata: TargetMetadata) {
        val customer = customerRepository.findById(metadata.userId).orElseThrow()
        repository.deleteALlByCustomer(customer)
        xpHistoryService.findAndWithdrawXp(metadata.targetId, userAction)
    }


}