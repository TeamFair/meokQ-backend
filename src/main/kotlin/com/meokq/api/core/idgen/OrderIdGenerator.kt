package com.meokq.api.core.idgen

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.Configurable
import org.hibernate.id.IdentifierGenerator
import org.hibernate.service.ServiceRegistry
import org.hibernate.type.Type
import java.sql.Connection
import java.util.*

class OrderIdGenerator : IdentifierGenerator, Configurable {
    private var tableName: String? = null
    private var whereColumnNames: List<String> = emptyList()

    override fun configure(type: Type?, parameters: Properties?, serviceRegistry: ServiceRegistry?) {
        tableName = parameters?.getProperty("tableName")
        val whereColumnNamesString = parameters?.getProperty("whereColumnNames")
        whereColumnNames = whereColumnNamesString?.split(",") ?: emptyList()
    }

    override fun generate(session: SharedSessionContractImplementor?, `object`: Any?): Any {
        var connection: Connection? = null
        return try {
            if (session == null) throw HibernateException("Unable to generate ID")

            val sql = makeQuery(`object`)
            connection = session.jdbcConnectionAccess.obtainConnection()
            val stmt = connection.prepareStatement(sql)
            val rs = stmt.executeQuery()
            if (rs.next()) {
                rs.getLong(1) + 1
            } else {
                throw HibernateException("Unable to generate ID")
            }
        } catch (ex: Exception) {
            throw HibernateException("Unable to generate ID", ex)
        } finally {
            connection?.close()
        }
    }

    private fun objectToMap(obj: Any?): Map<String, Any>? {
        return try {
            val objectMapper = jacksonObjectMapper()
            val json = objectMapper.writeValueAsString(obj)
            objectMapper.readValue(json, Map::class.java) as Map<String, Any>?
        } catch (ex: Exception) {
            null
        }
    }

    private fun makeQuery(obj: Any?): String {
        val baseQuery = StringBuilder("select count(1) from $tableName ")

        objectToMap(obj)?.let { data ->
            whereColumnNames.forEachIndexed { index, columnName ->
                val value = data[columnName]
                val joinStr = if (index == 0) "where" else "and"
                if (value != null) {
                    val snakeColumnName = camelCaseToSnakeCase(columnName)
                    baseQuery.append(" $joinStr $snakeColumnName='$value' ")
                }
            }
        }

        return baseQuery.toString()
    }

    private fun camelCaseToSnakeCase(input: String): String {
        val regex = "([a-z])([A-Z]+)".toRegex()
        return input.replace(regex, "$1_$2").lowercase(Locale.getDefault())
    }
}