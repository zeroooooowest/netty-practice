package me.zw

import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.Bean

@ConfigurationPropertiesScan
@SpringBootApplication
class NettyServerApplication {

    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): EventLoopGroup {
        return NioEventLoopGroup(2)
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): EventLoopGroup {
        return NioEventLoopGroup(5)
    }
}

interface NettyServer {
    fun start()
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(NettyServerApplication::class.java)
        .build()
        .run(*args)
}