package com.nettybook.ch5;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServerWithFuture 
{
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoServerHandlerWithFuture());
				}
			});
			
			// ChannelFuture f = sb.bind(8888).sync();
			/* 에코서버가 8888번 포트를 사용하도록 바인드하는 비동기 bind 메서드를 호출한다.
			 * 부트스트랩 클래스의 bind 메서드는 포트바인딩이 완료되기 전에 ChannelFuture 객체를 돌려준다. */
			ChannelFuture bindFuture = sb.bind(8888);
			System.out.println("Bind 시작...");
			/* ChannelFuture 인터페이스의 sync 메서드는 주어진 ChannelFuture 객체의 작업이 완료될 때까지 블로킹하는 메서드이다.
			 * 그러므로 bind 메서드의 처리가 완료될 때 sync 메서드도 같이 완료된다. */
			bindFuture.sync();
			System.out.println("Bind 완료...");
			
			// f.channel().closeFuture().sync();
			/* bindFuture 객체를 통해서 채널을 얻어온다.
			 * 여기서 얻어진 채널은 8888번 포트에 바인딩된 서버채널이다. */
			Channel srvChannel = bindFuture.channel();
			/* 바인드가 완료된 서버채널의 CloseFuture 객체를 돌려준다. 
			 * 네티 내부에서는 채널이 생성될 때 CloseFuture 객체도 같이 생성되므로 clsFuture 메서드가 돌려주는 CloseFuture 객체는 항상 동일한 객체다. */
			ChannelFuture clsFuture = srvChannel.closeFuture();
			/* CloseFuture 객체는 채널의 연결이 종료될 때 연결종료 이벤트를 받는다.
			 * 채널이 생성될 때 같이 생성되는 기본 CloseFuture 객체에는 아무 동작도 설정되어 있지 않으므로 이벤트를 받았을 때 아무 동작도 하지 않는다. */
			clsFuture.sync();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

}
