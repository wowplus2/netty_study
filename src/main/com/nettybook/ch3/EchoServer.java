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
		EventLoopGroup bossGrp = new NioEventLoopGroup(1);
		EventLoopGroup workerGrp = new NioEventLoopGroup();
		
		try {
			/* Bootstrap 클래스가 빌더패턴(메서드 호출 결과로 자신의 객체참조를 돌려주는 프로그래밍을 구현하는 패턴의 일종)
			 * 으로 작성되어 있으므로 인수없는 생성자로 객체를 생성하고 group, channel과 같은 메서드로 객체를 초기화한다. */
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(bossGrp, workerGrp)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					
					p.addLast(new EchoServerHandler());
				}
			});
			
			ChannelFuture f = sb.bind(8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}
	}

}
