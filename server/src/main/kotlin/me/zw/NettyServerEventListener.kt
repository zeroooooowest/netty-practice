package me.zw

import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener

@Configuration
class NettyServerEventListener(
    private val echoServer: NettyServer
) {

    @EventListener(ContextRefreshedEvent::class)
    fun handlerContextRefreshedEvent() {
        echoServer.start()
    }
}