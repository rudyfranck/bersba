package  com.rsba.order_microservice.database

import com.rsba.order_microservice.domain.model.Order
import io.r2dbc.spi.Row
import io.r2dbc.spi.RowMetadata
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*


object OrderDBHandler {

    private val jsonHandler = Json {
        ignoreUnknownKeys = true
        isLenient = true
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "#class"
    }

    fun all(row: Row?, meta: RowMetadata? = null): List<Order> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                jsonHandler.decodeFromString("""$json""")
            } else {
                mutableListOf()
            }
        } else {
            mutableListOf()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        listOf()
    }


    fun one(row: Row?, meta: RowMetadata? = null): Optional<Order> = try {
        if (row != null) {
            val json = row.get(0, String::class.java)
            if (json != null) {
                Optional.ofNullable(
                    jsonHandler.decodeFromString<List<Order>>("""$json""").firstOrNull()
                )
            } else {
                Optional.empty()
            }
        } else {
            Optional.empty()
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Optional.empty()
    }


    private inline fun <reified N> meOrNull(row: Row, index: Int): N? = try {
        val i = row.get(index, N::class.java)
        println { "Count = $i" }
        i
    } catch (e: Exception) {
        null
    }

    fun count(row: Row, meta: RowMetadata? = null): Int {
        return try {
            meOrNull(row = row, index = 0) ?: 0
        } catch (e: Exception) {
            0
        }
    }

    fun countAsString(row: Row, meta: RowMetadata? = null): String? {
        return try {
            meOrNull(row = row, index = 0)
        } catch (e: Exception) {
            null
        }
    }

}