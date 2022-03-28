package com.rsba.parameters_microservice.resolver.query

import com.rsba.parameters_microservice.configuration.request_helper.CustomPageInfo
import com.rsba.parameters_microservice.domain.format.ICursorUtil
import graphql.relay.*
import java.util.*

interface GenericRetrieveConnection : ICursorUtil {

    fun <T> perform(entries: List<T>, first: Int, after: UUID? = null): Connection<T> {
        val edges: List<Edge<T>> = entries.filterNotNull().map { source ->
            val fieldId = source::class.java.fields.singleOrNull { f ->
                f.name.contentEquals("id", ignoreCase = true)
            }
            return@map DefaultEdge(source, createCursorWith(id = (fieldId?.get(source) as? UUID?) ?: UUID.randomUUID()))
        }.take(first)
        val pageInfo = CustomPageInfo(edges = edges, first = first, after = after)
        return DefaultConnection(edges, pageInfo)
    }

    fun <T> perform(entries: Map<UUID, List<T>>, first: Int, after: UUID? = null, id: UUID): Connection<T> {
        val entriesMap = if (entries.containsKey(id) && entries[id] != null) {
            entries[id]!!
        } else {
            emptyList()
        }
        val edges: List<Edge<T>> = entriesMap.filterNotNull().map { source ->
            val fieldId = source::class.java.fields.singleOrNull { f ->
                f.name.contentEquals("id", ignoreCase = true)
            }
            return@map DefaultEdge(source, createCursorWith(id = (fieldId?.get(source) as? UUID?) ?: UUID.randomUUID()))
        }.take(first)
        val pageInfo = CustomPageInfo(edges = edges, first = first, after = after)
        return DefaultConnection(edges, pageInfo)
    }

}