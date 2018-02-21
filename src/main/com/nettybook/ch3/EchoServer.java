package com.nettybook.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// 부모 쓰레드그룹은 단일 쓰레드로 동작한다.
		/* EventLoopGroup 인터페이스에 NioEventLoopGroup 클래스의 객체를 할당한다.
		 * 생성자에 입력된 쓰레드 수가 1이므로 단일쓰레도로 동작하는 NioEventLoopGroup 객체를 생성한다. */
		EventLoopGroup bossGrp = new NioEventLoopGroup(1);
		// 사용할 쓰레드 수를 서버어플리케이션이 동작하는 H/W 코어 수를 기준으로 결정한다. 
		// 일반적으로 CPU 코어 수의 2배를 사용한다.
		/* EventLoopGroup 인터페이스에 NioEventLoopGroup 클래스의 객체를 할당한다. 생성자에 인수가 없으므로 
		 * CPU 코어 수에 따른 쓰레드 수가 설정된다. */
		EventLoopGroup workerGrp = new NioEventLoopGroup();
		
		try {
			/* Bootstrap 클래스가 빌더패턴(메서드 호출 결과로 자신의 객체참조를 돌려주는 프로그래밍을 구현하는 패턴의 일종)
			 * 으로 작성되어 있으므로 인수없는 생성자로 객체를 생성하고 group, channel과 같은 메서드로 객체를 초기화한다. */
			/* ServerBootstrap을 생성한다. */
			ServerBootstrap sb = new ServerBootstrap();
			/* ServerBootstrap에 생성한 NioEventLoopGroup 객체를 인수로 입력한다.
			 * 첫번째 인수는 부모 쓰레드이다. 부모 쓰레드는 클라이언트 연결 요청의 수락을 담당한다.
			 * 두번째 인수는 연결된 소켓에 대한 I/O처리를 담당하는 자식 쓰레드이다. */
			sb.group(bossGrp, workerGrp)
			/* 서버소켓(부모쓰레드)이 사용할 네트워크 입출력 모드를 설정한다.
			 * 여기서는 NioServerSocketChannel 클래스를 설정했기 때문에 NIO 모드로 작동한다. */
			.channel(NioServerSocketChannel.class)
			/* 자식채널의 초기화 방법을 설정한다. 여기서는 익명클래스로 채널 초기화 방법을 지정했다. */
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				/* ChannelInitializer는 클라이언트로부터 연결된 채널이 초기화될때의 기본동작이 지정된 추상클래스이다. */
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					/* 채널 파이프라인 객체를 생성한다. */
					ChannelPipeline p = ch.pipeline();
					/* 채널 파이프라인에 EchoServerHandler 클래스를 등록한다.
					 * EchoServerHandler 클래스는 이후에 클라이언트의 연결이 생성되었을떄 데이터 처리를 담당한다. */
					p.addLast(new EchoServerHandler());
				}
			});
			
			ChannelFuture f = sb.bind(8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			workerGrp.shutdownGracefully();
			bossGrp.shutdownGracefully();
		}
	}

}
