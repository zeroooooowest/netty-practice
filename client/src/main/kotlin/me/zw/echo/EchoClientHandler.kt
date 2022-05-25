package me.zw.echo

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandler.Sharable
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil
import mu.KotlinLogging
import org.springframework.stereotype.Component

/*
  # 클라이언트에서 SimpleChannelInboundHandler 를 사용한 이유?
    (서버 쪽은 ChannelInboundHandlerAdapter)
  : 클라이언트 쪽 channelRead0() 메서드가 완료된 시점에는 들어온 메시지가 이미 확보됐고 이용도 끝난 상태다
   따라서 메서드가 리턴될 때 SimpleChannelInboundHandler 는 메시지가 들어있는 ByteBuf 에 대한 메모리 참조를 해제한다.

   반면, EchoServerHandler 에서는 아직 들어오는 메시지를 발신자에게 에코 출력해야 하며,
   channelRead() 가 리턴될 때까지 비동기식인 write() 작업이 완료되지 않았을 수 있다.
   따라서 EchoServerHandler 는 이 시점까지 메시지를 해제하지 않는 ChannelInboundHandler 를 확장한다..
   결국 channelReadComplete() 에서 writeAndFlush() 메서드가 호출될 때 메시지는 해제된다.
 */
@Component
@Sharable
class EchoClientHandler : SimpleChannelInboundHandler<ByteBuf>() {

    companion object {
        private val logger = KotlinLogging.logger { }
    }

    override fun channelActive(ctx: ChannelHandlerContext) {
        // 채널 활성화 알림을 받으면 메시지를 전송
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty go!", CharsetUtil.UTF_8))
    }

    /*
      주의할 점은 서버가 전송한 메시지가 여러 청크로 수신될 수 있다는 점.
      즉, 서버가 5바이트를 전송할 때 5바이트가 모두 한 번에 수신된다는 보장이 없다.
      데이터가 적은 경우에도 이 메서드가 두번 호출될 수 있다. (다만 TCP 는 스트림 기반 프로토콜이므로 순서는 보장)
     */
    override fun channelRead0(ctx: ChannelHandlerContext, msg: ByteBuf) {
        // 수신한 메시지의 덤프를 로깅
        logger.info { "Client received: ${msg.toString(CharsetUtil.UTF_8)}" }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}