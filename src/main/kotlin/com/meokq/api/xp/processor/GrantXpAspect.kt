package com.meokq.api.xp.processor

/*
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
}*/
