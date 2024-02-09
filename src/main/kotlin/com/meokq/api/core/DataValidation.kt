package com.meokq.api.core

import com.meokq.api.core.exception.DataException

object DataValidation {
    fun checkNotNullData(value : Any?, message : String){
        try{ requireNotNull(value) }
        catch (e : IllegalArgumentException){
            throw DataException("DataException : $message")
        }
    }
}