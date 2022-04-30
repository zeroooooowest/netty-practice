package me.zw

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class NettyClientApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder(NettyClientApplication::class.java)
        .build()
        .run(*args)
}