package me.zw.echo

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component

@Sharable
@Component
class EchoServerHandler : ChannelInboundHandlerAdapter() {

    companion object {
        val logger = KotlinLogging.logger { }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val inbound = msg as ByteBuf

        logger.info { "Server received: ${inbound.toString(CharsetUtil.UTF_8)}" }

        ctx.write(inbound)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
            .addListener { ChannelFutureListener.CLOSE }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        super.exceptionCaught(ctx, cause)
        cause.printStackTrace()
        ctx.close()
    }
}