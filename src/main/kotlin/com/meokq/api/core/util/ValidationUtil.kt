package com.meokq.api.core.util

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