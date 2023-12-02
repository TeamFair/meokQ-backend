package com.meokq.api.auth.client

interface AuthClient {
    fun validateToken(req : Any) : Boolean
    fun deleteToken(req : Any)
    fun unlink(req : Any) : Any
}