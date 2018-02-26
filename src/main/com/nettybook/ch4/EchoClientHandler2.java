package com.nettybook.ch4;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


public class EchoClientHandler2 extends ChannelInboundHandlerAdapter 
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		String sMsg = "Hello Netty~";
		
		ByteBuf msgBuffer = Unpooled.buffer();
		msgBuffer.writeBytes(sMsg.getBytes());
		
		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열 [");
		builder.append(sMsg);
		builder.append("]");
		
		System.out.println(builder.toString());
		ctx.write(msgBuffer);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String rMsg = ((ByteBuf) msg).toString(Charset.defaultCharset());
		
		StringBuilder builder = new StringBuilder();
		builder.append("수신된 문자열 [");
		builder.append(rMsg);
		builder.append("]");
		
		System.out.println(builder.toString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}
