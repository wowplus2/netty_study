package com.nettybook.ch4;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

public class EchoClient 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		EventLoopGroup grp = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			
			b.group(grp)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoClientHandler1());
					p.addLast(new EchoClientHandler2());
					p.addLast(new LoggingHandler());
				}
			});
			
			ChannelFuture f = b.connect("localhost", 8888).sync();
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			grp.shutdownGracefully();
		}
	}

}
