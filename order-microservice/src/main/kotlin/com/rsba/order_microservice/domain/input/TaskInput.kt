package com.rsba.order_microservice.domain.input

import com.rsba.order_microservice.configuration.deserializer.DateTimeSerializer
import kotlinx.serialization.Serializable
import java.util.*
import com.rsba.order_microservice.configuration.deserializer.UUIDSerializer
import com.rsba.order_microservice.configuration.scalar.BigDecimalSerializer
import com.rsba.order_microservice.domain.format.ModelType
import com.rsba.order_microservice.domain.format.ModelTypeCase
import java.math.BigDecimal
import java.time.OffsetDateTime

@Serializable
@ModelType(_class = ModelTypeCase.tasks)
data class TaskInput(
    @Serializable(with = UUIDSerializer::class) override val id: UUID,
    @Serializable(with = BigDecimalSerializer::class) val quantity: BigDecimal? = null,
    @Serializable(with = BigDecimalSerializer::class) val estimatedTimeInHour: BigDecimal? = null,
    val description: String? = null,
    @Serializable(with = DateTimeSerializer::class) val estimatedEndDate: OffsetDateTime? = null,
    val departments: List<HostInput>? = emptyList(),
) : AbstractInput()
