package com.nettybook.ch7.telnetserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TelnetServer 
{
	/* 텔넷 서버의 서비스에 사용할 포트를 8888번으로 지정한다. */
	private static final int PORT = 8888;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			/* 텔넷 서버의 채널 파이프라니 설정 코드가 작성된 TelnetServerInitializer 클래스를 부트스트랩에 지정한다. */
			.childHandler(new TelnetServerInitializer());
			
			/* 부트스트랩에 텔넷 서비스 포트를 지정하고 서버 소켓에 바인딩된 채널이 종료될 때까지 대기하도록 설정한다. 
			 * 여기서 ChannelFuture 인터페이스의 sync 메서드는 지정한 Future 객체의 동작이 완료될 때가지 대기하는 메서드이다. */
			ChannelFuture f = sb.bind(PORT).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

}
