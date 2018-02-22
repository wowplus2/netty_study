package com.nettybook.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServerV4 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup bossGrp = new NioEventLoopGroup(1);
		EventLoopGroup workerGrp = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGrp, workerGrp)
			.channel(NioServerSocketChannel.class)
			//.handler(new LoggingHandler(LogLevel.INFO))
			/* 특히 서버 어플리케이션이 동작 중인 운영체제에서 TIMW_WAIT이 많이 생기는 것을 방지하기 위해 사용하거나,
			 * Proxy 서버가 동작 중인 운영체제에서 TIME_WAIT이 발생하는 것을 방지하기 위해 사용함. */
			.childOption(ChannelOption.SO_LINGER, 0)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new LoggingHandler(LogLevel.INFO));
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
