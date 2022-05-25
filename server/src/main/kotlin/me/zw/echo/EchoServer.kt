package me.zw.echo

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import me.zw.NettyServer
import org.springframework.stereotype.Component
import java.net.InetSocketAddress

@Component
class EchoServer(
    private val echoServerProperties: EchoServerProperties,
    private val echoServerHandler: EchoServerHandler,
    private val bossGroup: EventLoopGroup,
    private val workerGroup: EventLoopGroup,
) : NettyServer {

    override fun start() {
        val b = ServerBootstrap()
        val channelFuture = b.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel::class.java)
            .childHandler(channelInitializer())
            .bind(echoServerProperties.port)
            .sync() // 서버를 비동기식으로 바인딩.

        // 채널의 CloseFuture 를 얻고 완료될 때까지 현재 스레드를 블로킹.
        channelFuture.channel().closeFuture().sync()
    }

    // 새로운 연결을 수락한 후, 새로운 자식 Channel 을 생성하며,
    // ChannelInitializer 가 EchoServerHandler 의 인스턴스 하나를 Channel 의 ChannelPipeline 으로 추가한다.
    private fun channelInitializer() = object : ChannelInitializer<SocketChannel>() {
        override fun initChannel(ch: SocketChannel) {
            ch.pipeline().addLast(echoServerHandler)
        }
    }


}