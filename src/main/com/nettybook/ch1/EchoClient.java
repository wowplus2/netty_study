package com.nettybook.ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/*
 * Sends one message when a connection is open and echoes back any received data to the server.
 * Simply put, the echo client initiates the ping-pong traffic between the echo client and server
 * by sending the first message to the server.
*/
public class EchoClient 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bs = new Bootstrap();
			
			/*
			 * ServerBootstrap 설정과 다르게 EventLoopGroup이 하나만 설정되었다.
			 * client application은 서버와 달리 서버에 연결된 channel이 하나만 존재하기 때문에 EventLoopGroup이 하나이다. */
			bs.group(group);
			/*
			 * client application이 생성하는 channel의 종류를 설정한다. 
			 * 여기서는 NIO 소켓채널인 NioSocketChannel클래스를 설정했다. 즉 서버에 연결된 client의 소켓채널은 NIO로 동작하게 된다. */
			bs.channel(NioSocketChannel.class);
			/*
			 * client application 이므로, 채널 파이프라인 설정에 일반 소켓 채널 클래스인 SocketChannel을 설정한다. */
			bs.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler());
				}
			});
			
			/*
			 * 비동기 입출력 메서드인 connect를 호출한다. 
			 * connect 메서드는 메서드의 호출 결과로 ChannelFuture 객체를 돌려주는데 이 객체를 통해서 비동기 메서드의 처리 결과를 확인할 수 있다.
			 * ChannelFuture 객체의 sync 메서드는 ChannelFuture 객체의 요청이 완료될 때까지 대기한다.
			 * 단, 요청이 실패하면 예외를 던진다. 즉 connect 메서드의 처리가 완료될때까지 다음 라인으로 진행하지 않는다. 
			*/
			ChannelFuture f = bs.connect("localhost", 8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			group.shutdownGracefully();
		}
	}

}
