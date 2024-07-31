package com.meokq.api.xp.processor

import com.meokq.api.core.AuthDataProvider
import com.meokq.api.user.service.CustomerService
import com.meokq.api.xp.annotations.GrantXp
import com.meokq.api.xp.annotations.ReturnXp
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Aspect
@Component
@Deprecated("240731 구조 변경")
class GrantXpAspect: AuthDataProvider {

    @Autowired
    private lateinit var applicationContext: ApplicationContext
    @Autowired
    private lateinit var customerService: CustomerService

    @Around("@annotation(grantXp)")
    fun grantAspect(joinPoint: ProceedingJoinPoint, grantXp: GrantXp): Any? {
        val processorBean = applicationContext.getBean(grantXp.processor.java)
        val args = joinPoint.args.toList()
        val result = joinPoint.proceed()
       // val xpReq = processorBean.getXpReq()
        //customerService.gainXp(xpReq)

        return result
    }

    @Around("@annotation(returnXp)")
    fun returnAspect(joinPoint: ProceedingJoinPoint, returnXp: ReturnXp): Any? {
        val processorBean = applicationContext.getBean(returnXp.returnProcessor.java)
        val args = joinPoint.args.toList()
        val result = joinPoint.proceed()
       // val xpReq = processorBean.returnXpReq()
        //customerService.gainXp(xpReq)

        return result
    }
}