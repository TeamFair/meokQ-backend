package com.meokq.api.xp.processor

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.annotations.GrantXp
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Aspect
@Component
class GrantXpAspect: AuthDataProvider {

    @Autowired
    private lateinit var applicationContext: ApplicationContext
    @Autowired
    private lateinit var customerService: CustomerService

    @Around("@annotation(grantXp)")
    fun grantAspect(joinPoint: ProceedingJoinPoint, grantXp: GrantXp) {
        val processorBean = applicationContext.getBean(grantXp.processor.java)
        val args = joinPoint.args.toList()
        val result = joinPoint.proceed()

        if (processorBean.isTarget()){
            val xpReq = processorBean.getXpReq()
            customerService.gainXp(getAuthReq(), xpReq)
        }
    }
}