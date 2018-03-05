package com.nettybook.ch8;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@Sharable
public class TelnetServerHandler extends SimpleChannelInboundHandler<String>
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// Send greeting for a new connection.
		ctx.write("환영합니다.\r\n");
		ctx.write(InetAddress.getLocalHost().getHostName() + " 에 접속하셨습니다.\r\n");
		ctx.write("현재 시간은 " + new Date() + " 입니다.\r\n");
		ctx.flush();
	}
	
	@Override
	/* 1. 사용자 입력 메세지를 확인한다.
	 * 2. 사용자가 입력한 메세지의 종류에 따라서 응답 메세지를 생성한다.
	 *    메세지는 세 종류로 구분된다. 입력한 메세지가 없을때, 입력한 메세지가 종료 메세지(bye)일 때, 앞의 두 종류에 포함되지 않을 때이다.
	 * 3. 응답 메세지를 채널 버퍼에 기록한다.
	 * 4. 입력된 메세지가 종료 메세지이면 ChannelFuture 객체에 채널 종료 리스너를 할당한다. */
	protected void channelRead0(ChannelHandlerContext ctx, String req) throws Exception {
		// TODO Auto-generated method stub
		String resp;
		boolean close = false;
		
		if (req.isEmpty()) {
			resp = "명령을 입력해 주세요.\r\n";
		}
		else if ("bye".equals(req.toLowerCase())) {
			resp = "좋은 하루 되세요~\r\n";
			close = true;
		}
		else {
			resp = "입력하신 명령이 " + req + " 입니까?\r\n";
		}
		
		ChannelFuture f = ctx.write(resp);
		
		if (close) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
		
}
