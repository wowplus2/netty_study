package com.nettybook.ch7.telnetserver;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/* Sharable 어노테이션은 네티가 제공하는 공유가능 상태표시 어노테이션이다.
 * Sharable로 지정된 클래스를 채널 파이프라인에서 공유할 수 있다는 의미이다.
 * 즉, 다중 스레드에서 스레드 경합 없이 참조가 가능하다.
 * Sharable 어노테이션이 지정된 대표적인 클래스로서 StringDecoder와 StringEncoder가 있으며 대부분 codec 패키지에 속한다.
 * ※ API 문서참조: ChannelHandler.Sharable */
@Sharable
/* 여기에 지정된 제너릭 타입은 데이터 수신 이벤트인 channelRead0 메서드의 두 번째 인수의 데이터형이 된다.
 * TelnetServerHandler 클래스에서는 수신된 데이터가 String 데이터임을 의미한다. */
public class TelnetServerHandler extends SimpleChannelInboundHandler<String>
{	
	@Override
	/* channelActive 메서드는 채널이 생성된 다음 바로 호출되는 이벤트이다.
	 * 서버 프로그램을 예로 들면 클라이언트가 서버에 접속되면 네티의 채널이 생성되고 해당 채널이 활성화되는데 이때 호출된다.
	 * 통상적으로 채널이 연결된 직후에 수행할 작업을 처리할때 사용되는 이벤트이다. */
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// Send greeting for a new connection.
		/* 위에서 설명한 바와 같이 클라이언트가 처음 접속되었을때 클라이언트로 환영메세지를 전송하는 데 사용한다. */
		ctx.write("환영합니다. " + InetAddress.getLocalHost().getHostName() + " 에 접속하셨습니다.\r\n");
		ctx.write("현재 시간은 " + new Date() + " 입니다.\r\n");
		/* 채널에 기록된 데이터를 즉시 클라이언트로 전송한다. */
		ctx.flush();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String req) throws Exception {
		// TODO Auto-generated method stub
		String resp;
		boolean close = false;
		
		/* 클라이언트로부터 수신된 명령어 처리를 위해 분기한다.
		 * 클라이언트로부터 수신된 데이터가 공백 문자열이면 수행되는 코드이다.
		 * 즉 클라이언트가 콘솔에서 아무런 문자열도 입력하지 않고 엔터키를 입력한 상태를 말한다. */
		if (req.isEmpty()) {
			resp = "명령어를 입력 하세요.\r\n";
		}
		/* 클라이언트로부터 수신된 명령어가 bye이면 종료 메세지를 생성하고 종료 명령 플래그를 true로 지정한다. */
		else if ("bye".equals(req.toLowerCase())) {
			resp = "Have a good time~\r\n";
			close = true;
		}
		/* 클라이언트가 bye가 아닌 정상적인 명령을 입력했다면 입력한 명령어와 명령어 확인 메세지를 생성한다. */
		else {
			resp = "입력하신 명령이 '" + req + "'이(가) 맞습니까?\r\n";
		}
		
		/* 명령어 분기에 따라서 생성된 메세지를 채널에 기록한다. */
		ChannelFuture f = ctx.write(resp);
		
		/* 종료 명령 플래그를 확인하여 연결된 클라이언트의 채널을 닫는다.
		 * 이때 ChannelFuture에 ChannelFutureListener.CLOSE를 등록하여 비동기로 채널을 닫는다. */
		if (close) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		/* channelReadComplete 이벤트는 channelRead0 이벤트가 완료되면 호출되는 이벤트이다.
		 * 여기서 ChannelHandlerContext의 flush 메서드를 사용하여 채널에 기록된 데이터를 클라이언트로 즉시 전송한다. */
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
}
