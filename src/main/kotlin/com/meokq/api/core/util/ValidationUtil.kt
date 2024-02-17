package com.meokq.api.core.util

@Deprecated("DataValidation 사용")
object ValidationUtil {
    fun checkNotNullData(
        value : Any?,
        exception: Exception
    ){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw exception
        }
    }
}