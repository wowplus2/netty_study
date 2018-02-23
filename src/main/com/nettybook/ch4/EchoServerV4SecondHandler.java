package com.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoServerV4SecondHandler extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// TODO Auto-generated method stub
		ByteBuf rMsg = (ByteBuf) msg;
		
		System.out.println("EchoServerV4SecondHandler::channelRead: " + rMsg.toString(Charset.defaultCharset()));
		ctx.write(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		// TODO Auto-generated method stub
		System.out.println("EchoServerV4SecondHandler::channelReadComplete 이벤트 발생...");
		ctx.flush();
	}

	/*@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}*/
	
}
