package com.nettybook.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServerV3 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup bossGrp = new NioEventLoopGroup();
		EventLoopGroup workerGrp = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGrp, workerGrp)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new LoggingHandler(LogLevel.DEBUG));
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
