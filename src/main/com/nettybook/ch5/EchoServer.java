package com.nettybook.ch5;

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
					
					p.addLast(new EchoServerHandler());
				}
			});
			
			/* 퓨처패턴 구현체인 ChannelFuture 객체를 사용함. */
			ChannelFuture f = sb.bind(8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}

}
