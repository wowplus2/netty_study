package com.nettybook.ch8.junit;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Sharable
public class TelnetServerHandlerV3 extends SimpleChannelInboundHandler<String> 
{
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// Send greeting for a new connection.
		/* 새로운 클라이언트가 접속되었을때 ResponseGenerator의 makeGreetingMessage 메서드를 사용하여 정송할 환영 메세지를 생성하고
		 * 그 결과를 클라이언트로 전송한다. makeGreetingMessage 메서드는 입력 파라메터와 상관없이 메세지를 생성 하므로
		 * 정적 메서드로 선언했다. */
		ctx.write(ResponseGenerator.makeGreetingMessage());
		ctx.flush();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String req) throws Exception {
		// TODO Auto-generated method stub
		/* 사용자가 입력한 메세지를 입력으로 ResponseGenerator 클래스의 객체를 생성한다. */
		ResponseGenerator rg = new ResponseGenerator(req);
		
		// Generate and write a response.
		/* ResponseGenerator 객체의 response 메서드로 입력된 메세지에 해당하는 응답 문자열을 생성한다. */
		String resp = rg.response();
		
		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the conversion.
		/* 입력된 메세지에 해당하는 응답 문자열을 전송한다. */
		ChannelFuture f = ctx.write(resp);
		
		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'
		/* ResponseGenerator 객체의 isClose 메서드로 사용자가 입력한 메세지가 연결 종료 문자열인지 확인하여
		 * 연결 종료 리스너 등록 여부를 결정한다. */
		if (rg.isClose()) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
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
