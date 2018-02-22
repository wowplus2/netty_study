package com.nettybook.ch3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/* Sends one message when a connection is open and echoes back any received data to the server.
 * Simply put, the echo client initiates the ping-pong traffic between the echo client and server by sending the first message to the server. */
public class EchoClient 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			/* 클라이언트 어플이케이션을 위한 Bootstrap 객체를 생성한다. */
			Bootstrap b = new Bootstrap();
			
			b.group(group)
			.channel(NioSocketChannel.class)
			/* 클라이언트 소켓채널의 이벤트 핸들러를 설정하기 위해서 ChannelInitializer 추상클래스의 객체를 생성한다. */
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					/* ChannelInitializer 클래스에 initChannel메서드의 이벤트 핸들러인 EchoClientHandler를 등록한다. */
					p.addLast(new EchoClientHandler());
				}
			});
			
			ChannelFuture f = b.connect("localhost", 8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			group.shutdownGracefully();
		}
	}

}
