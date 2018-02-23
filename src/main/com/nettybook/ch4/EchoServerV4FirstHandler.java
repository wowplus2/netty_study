package com.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoServerV4FirstHandler extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// TODO Auto-generated method stub
		ByteBuf rMsg = (ByteBuf) msg;
		
		System.out.println("EchoServerV4FirstHandler::channelRead: " + rMsg.toString(Charset.defaultCharset()));
		ctx.write(msg);
		/* EchoServerV4SecondHandler 메서드가 수행되게 한다. */
		ctx.fireChannelRead(msg);
	}
}
