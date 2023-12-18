package com.meokq.api.core.converter

import com.meokq.api.core.util.ReflectionUtils
import kotlin.reflect.KClass

open class BaseConverterV2<T:Any>(
    val kClass : KClass<T>
){
    open fun <F>convertTo(fromSource : F) : T {
        return ReflectionUtils.convertTo(
            fromSource = fromSource!!,
            destinationClass = kClass,
        )
    }

    open fun <F>overWrite(
        fromSource : F,
        toSource : T
    ){
        ReflectionUtils.overwrite(
            fromSource = fromSource!!,
            toSource = toSource,
        )
    }
}