package me.zw.echo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "echo-client")
data class EchoClientProperties(
    val server: Server
) {
}

data class Server(
    val host: String,
    val port: Int,
)