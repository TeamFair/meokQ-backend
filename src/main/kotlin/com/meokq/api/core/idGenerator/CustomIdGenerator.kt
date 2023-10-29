package com.meokq.api.core.idGenerator

import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.util.*

class CustomIdGenerator : IdentifierGenerator, Configurable {
    // 전달받은 속성값
    private var sequenceName : String? = null
    private var prefix : String? = null

    // 속성값 처리
    override fun configure(type: Type?, parameters: Properties?, serviceRegistry: ServiceRegistry?) {
        sequenceName = parameters?.get("sequenceName") as String?
        prefix = parameters?.get("prefix") as String?
    }

    // id 채번
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        return try {
            if (session == null) throw HibernateException("Unable to generate Notice ID")

            val connection = session.jdbcConnectionAccess.obtainConnection()
            val stmt = connection.prepareStatement("select next value for $sequenceName")
            val rs = stmt.executeQuery()
            if (rs.next()) {
                prefix + String.format("%08d", rs.getLong(1))
            } else {
                throw HibernateException("Unable to generate Notice ID")
            }
        } catch (ex: Exception) {
            throw HibernateException("Unable to generate Notice ID", ex)
        }
    }
}