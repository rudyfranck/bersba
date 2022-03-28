package com.rsba.component_microservice.configuration.error

 import com.rsba.component_microservice.domain.exception.DuplicateLoginKeyError
 import graphql.GraphQLError
import graphql.kickstart.execution.error.GenericGraphQLError
import mu.KotlinLogging

object MyErrorGuesser {

    private val logger = KotlinLogging.logger { }

    fun guess(message: String): GraphQLError {
        val error = DuplicateLoginKeyError()
        if (error.fit(message)) {
            return error
        }

        logger.warn { "≠------ DuplicateLoginKeyError" }

        return GenericGraphQLError(message)
    }
}