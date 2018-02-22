package com.nettybook.ch4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServerV1 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			/* childHandler 메서드를 통해서 연결된 클라이언트 소켓 채널이 사용할 채널 파이프라인을 설정한다. 
			 * 이때 ChannelInitializer 인터페이스를 구현한 익명클래스를 작성하여 childHandler의 인자로 입력한다. */
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				/* initChannel 메서드는 클라이언트 소켓 채널이 생성될때 자동으로 호출되는데 
				 * 이때 채널 파이프라인의 설정을 수행한다. */
				// initChannel 메서드 본체는 부트스트랩이 초기화 될때 수행되며, 이때 서버 소켓 채널과 채널 파이프라인이 연결된다.
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					/* initChannel 메서드의 인자로 입력된 소켓채널(즉, 연결된 클라이언트 소켓채널)에 설정된 채널 파이프라인을 가져오게 되는데,
					 * 네티의 내부에서는 클라이언트 소켓채널을 생성할 때 빈 채널 파이프라인 객체를 생성하여 할당한다. */
					ChannelPipeline p = ch.pipeline();
					/* add 메서드를 사용하여 이벤트 핸들러인 EchoServerV1Handler 클래스를 채널 파이프라인에 등록한다. */
					p.addLast(new EchoServerV1Handler());
				}
			});
			
			ChannelFuture f = sb.bind(8888).sync();
			
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

}
