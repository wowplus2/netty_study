package com.nettybook.ch8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TelnetServer 
{
	/* Telnet 서버의 포트를 상수로 정의했다.
	 * 보통 기본 Telnet 서버가 23번 포트를 사용하고 있으므로 포트 충돌을 방지하기위해 8023번 포트를 사용했다. */
	private static final int _PORT = 8023;
		
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			/* 텔넷 서버의 채널 파이프라인을 초기화하는 클래스로서 
			 * ChannelInitializer 추상 클래스를 상속받은 TelnetServerInitializer 클래스를 지정했다. */
			.childHandler(new TelnetServerInitializer());
			
			/* 부트스트랩 객체의 bind 메소드의 처리가 완료될 때까지 대기한다.
			 * 처리가 완료되면 생성된 서버 채널에 대한 ChannelFuture 객체를 돌려준다. */
			ChannelFuture f = sb.bind(_PORT).sync();
			/* ChannelFuture 객체가 참조하는 서버 채널에 close 이벤트가 발생항 때까지 대기한다. */
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

}
