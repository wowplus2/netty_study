package com.nettybook.ch3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

public class BlockingEchoServer 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		// Oio : Old-Blocking-IO의 약자
		EventLoopGroup bossGrp = new OioEventLoopGroup(1);
		EventLoopGroup workderGrp = new OioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(bossGrp, workderGrp)
			.channel(OioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) {
					// TODO Auto-generated method stub
					ChannelPipeline p = ch.pipeline();
					p.addLast(new EchoServerHandler());
				}
			});
			
			ChannelFuture f = sb.bind(8888).sync();
			
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			workderGrp.shutdownGracefully();
			bossGrp.shutdownGracefully();
		}
	}

}
