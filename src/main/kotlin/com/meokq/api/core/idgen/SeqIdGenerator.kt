package com.meokq.api.core.idgen

import com.meokq.api.core.enums.SeqGenerator
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.sql.Connection
import java.util.*

class SeqIdGenerator: IdentifierGenerator, Configurable {
    // 전달받은 속성값
    private var sequenceName : String? = null
    private var prefix : String? = null
    private var seqGenerator : SeqGenerator? = null

    // 속성값 처리
    override fun configure(type: Type?, parameters: Properties?, serviceRegistry: ServiceRegistry?) {
        val seqGeneratorStr = parameters?.get("seqGenerator") as String?
        seqGeneratorStr?.let {
            val seqGenerator = SeqGenerator.fromString(it)
            sequenceName = seqGenerator.sequence.name
            prefix = seqGenerator.prefix.name
        }
    }

    // id 채번
    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        var connection : Connection? = null
        return try {
            if (session == null) throw HibernateException("Unable to generate seq ID")

            connection = session.jdbcConnectionAccess.obtainConnection()
            val stmt = connection.prepareStatement("select next value for $sequenceName")
            val rs = stmt.executeQuery()
            if (rs.next()) {
                prefix + String.format("%08d", rs.getLong(1))
            } else {
                throw HibernateException("Unable to generate ${seqGenerator?.prefix?.description} ID")
            }
        } catch (ex: Exception) {
            throw HibernateException("Unable to generate Notice ID", ex)
        } finally {
            connection?.close()
        }
    }
}