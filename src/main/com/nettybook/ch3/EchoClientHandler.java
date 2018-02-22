package com.nettybook.ch3;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/* Handler implementation for the echo client. 
 * It initiates the ping-pong traffic between the echo client and server by sending the first message to the server. */
public class EchoClientHandler extends ChannelInboundHandlerAdapter
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		String sndMsg = "Hello Netty~";
		
		ByteBuf msgBuffer = Unpooled.buffer();
		msgBuffer.writeBytes(sndMsg.getBytes());
		
		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열 [");
		builder.append(sndMsg);
		builder.append("]");
		
		System.out.println(builder.toString());
		ctx.writeAndFlush(msgBuffer);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		String recvMsg = ((ByteBuf) msg).toString(Charset.defaultCharset());
		
		StringBuilder builder = new StringBuilder();
		builder.append("수신한 문자열 [");
		builder.append(recvMsg);
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
