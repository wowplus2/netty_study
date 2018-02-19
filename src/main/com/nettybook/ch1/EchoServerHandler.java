package com.nettybook.ch1;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// Handlers a server-side channel.
public class EchoServerHandler extends ChannelInboundHandlerAdapter 
{
	// 데이터 수신 이벤트 처리 메서드.
	// client로부터 데이터의 수신이 이루어졌을 때 Netty가 자동으로 호출하는 메서드
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		// 수신된 데이터를 가지고 있는 Netty의 ByteBuf 객체로부터 문자열 데이터를 읽어온다.
		String readMsg = ((ByteBuf) msg).toString(Charset.defaultCharset());
		
		StringBuilder builder = new StringBuilder();
		builder.append("수신된 문자열 [");
		builder.append(readMsg);
		builder.append("]");
		
		System.out.println(builder.toString());	// 수신된 문자열을 console로 출력.
		// ctx는 ChannelHandlerContext 인터페이스의 객체로서 ChannelPipeline에 대한 이벤트를 처리한다.
		ctx.write(msg);
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
