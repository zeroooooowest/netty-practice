package me.zw.echo

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import me.zw.NettyClient
import org.springframework.stereotype.Component

@Component
class EchoClient(
    private val workerGroup: EventLoopGroup,
    private val echoClientHandler: EchoClientHandler,
    private val echoClientProperties: EchoClientProperties,
) : NettyClient {

    override fun start() {
        val b = Bootstrap()

        b.group(workerGroup)
            .channel(NioSocketChannel::class.java)
            .remoteAddress(echoClientProperties.server.host, echoClientProperties.server.port)
            .handler(channelInitializer())

        // 원격 피어로 연결하고 연결이 완료되기를 기다리기
        val channelFuture = b.connect().sync()

        // 채널이 닫힐 때까지 블로킹
        channelFuture.channel().closeFuture().sync()
    }

    private fun channelInitializer() = object : ChannelInitializer<SocketChannel>() {
        override fun initChannel(ch: SocketChannel) {
            ch.pipeline().addLast(echoClientHandler)
        }
    }
}