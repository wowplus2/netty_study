package com.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerV3FisrtHandler extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf rMsg = (ByteBuf) msg;
		
		System.out.println("EchoServerV3FisrtHandler::channelRead: " + rMsg.toString(Charset.defaultCharset()));
		ctx.write(msg);
	}
	
}
