package com.nettybook.ch1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

// Handles a server-side channel.
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> 
{
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception { // 지정한 포트로 접속한 client가 데이터를 전송하면 자동으로 실행된다.
		// TODO Auto-generated method stub
		// 아무것도 하지 않음.
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception { 
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
