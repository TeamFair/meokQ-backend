package com.meokq.api.logs.annotations

import com.meokq.api.logs.processor.XpProcessor
import java.lang.annotation.ElementType
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(ElementType.METHOD)
annotation class GrantXp(
    val processor: KClass<out XpProcessor>,
)
