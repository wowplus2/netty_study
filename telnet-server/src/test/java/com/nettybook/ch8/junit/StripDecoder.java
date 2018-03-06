package com.nettybook.ch8.junit;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@Sharable
public class StripDecoder extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object req) throws Exception {
		// TODO Auto-generated method stub
		String resp = (String) req + "a";
		
		ctx.writeAndFlush(Unpooled.wrappedBuffer(resp.getBytes()));
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
