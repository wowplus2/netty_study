package com.nettybook.ch6;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter 
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf rMsg = (ByteBuf) msg;
		System.out.println("channelRead: " + rMsg.toString(Charset.defaultCharset()));
		
		ByteBufAllocator byteBufAllocator = ctx.alloc();
		ByteBuf newBuffer = byteBufAllocator.buffer();
		
		// newBuffer 사용
		ctx.write(msg);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}
