package com.meokq.api.core.util

import java.math.BigInteger
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.RSAPublicKeySpec
import java.util.*

object RSAPublicKeyUtils {

    fun createFromJwtClaims(modulus: String, exponent: String): RSAPublicKey {
        val nBytes = Base64.getUrlDecoder().decode(modulus)
        val eBytes = Base64.getUrlDecoder().decode(exponent)

        val n = BigInteger(1, nBytes)
        val e = BigInteger(1, eBytes)

        val keySpec = RSAPublicKeySpec(n, e)
        val keyFactory = KeyFactory.getInstance("RSA")

        val publicKey = keyFactory.generatePublic(keySpec)

        if (publicKey !is RSAPublicKey) {
            throw IllegalStateException("생성된 키가 RSAPublicKey가 아닙니다: ${publicKey::class.java}")
        }

        return publicKey
    }
}
