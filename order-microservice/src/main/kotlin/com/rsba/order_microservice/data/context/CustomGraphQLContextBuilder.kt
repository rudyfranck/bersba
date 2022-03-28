package  com.rsba.order_microservice.data.context

import com.rsba.order_microservice.data.context.dataloader.DataLoaderRegistryFactory
import graphql.kickstart.execution.context.GraphQLContext
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder
import org.springframework.stereotype.Component
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.websocket.Session
import javax.websocket.server.HandshakeRequest

@Component
class CustomGraphQLContextBuilder(
    private val registry: DataLoaderRegistryFactory,
//    private val logger: KLogger,
//    private val tokenImpl: TokenImpl
) : GraphQLServletContextBuilder {

    override fun build(request: HttpServletRequest?, response: HttpServletResponse?): GraphQLContext {
        val userId = request?.getHeader("user_id")

//        logger.warn { "************ building session context…" }
//
//        val token = readToken(request = request)
//
//        logger.warn { "************ the token = $token" }

        val ctx = DefaultGraphQLServletContext.createServletContext()
            .with(request)
            .with(response)
            .with(registry.create(instanceId = UUID.randomUUID()))
            .build()

        return CustomGraphQLContext(user_id = userId, context = ctx)
    }

    override fun build(p0: Session?, p1: HandshakeRequest?): GraphQLContext {
        return DefaultGraphQLWebSocketContext.createWebSocketContext()
            .with(p0)
            .with(p1)
            .with(registry.create(instanceId = UUID.randomUUID()))
            .build()
    }

    override fun build(): GraphQLContext {
        TODO("Not yet implemented")
    }
}