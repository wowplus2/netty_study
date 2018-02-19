package com.nettybook.ch1;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 * Handler implementation for the echo client.
 * It initiates the ping-pong traffic between the echo client and server by sending the first message to the server.
*/
public class EchoClientHandler extends ChannelInboundHandlerAdapter 
{
	/*
	 * channelActive 이벤트 메서드는 ChannelInboundHandlerAdapter에 정의된 이벤트로 
	 * Socket Channel이 최초 활성화 되었을때 실행된다. */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		String sndMsg = "Hello Netty.";
		
		ByteBuf msgBuffer = Unpooled.buffer();
		msgBuffer.writeBytes(sndMsg.getBytes());
		
		StringBuilder builder = new StringBuilder();
		builder.append("전송한 문자열 [");
		builder.append(sndMsg);
		builder.append("]");
		
		System.out.println(builder.toString());
		/*
		 * writeAndFlush 메서드는 내부적으로 데이터 기록과 전송의 2가지 메서드를 호출한다.
		 * 첫번째는 채널에 데이터를 기록하는 write 메서드이며, 두번째는 채널에 기록된 데이터를 서버로 전송하는 flush 메서드이다. */
		ctx.writeAndFlush(builder);
	}

	/* 서버로부터 수신된 데이터가 있을때 호출되는 Netty 이벤트 메서드. */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		/* 서버로부터 수신된 데이터가 저장된 msg 객체에서 문자열 데이터를 추출한다. */
		String recvMsg = ((ByteBuf) msg).toString(Charset.defaultCharset());
		
		StringBuilder builder = new StringBuilder();
		builder.append("수신한 문자열 [");
		builder.append(recvMsg);
		builder.append("]");
		
		System.out.println(builder.toString());
	}

	/* 수신된 데이터를 모두 읽었을때 호출되는 이벤트 메서드. 
	 * channelRead 메서드의 수행이 완료되고 나서 자동으로 호출된다. */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		/* 수신된 데이터를 모두 읽은 후 서버와 연결된 채널을 닫는다. 
		 * 이후 데이터 송수신 채널은 닫히게 되고 클라이언트 프로그램은 종료된다. */
		ctx.close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}
