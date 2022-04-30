package me.zw

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class NettyServerApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(NettyServerApplication::class.java)
        .build()
        .run(*args)
}