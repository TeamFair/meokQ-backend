package com.meokq.api.xp.annotations

import com.meokq.api.xp.processor.XpReturnProcessor
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(ElementType.METHOD)
@Deprecated("240731 구조 변경")
annotation class ReturnXp(
    val returnProcessor: KClass<out XpReturnProcessor>,
    )
