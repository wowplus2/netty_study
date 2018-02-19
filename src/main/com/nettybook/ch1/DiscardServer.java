package com.nettybook.ch1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer 
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
					// 접속된 client로부터 수신된 데이터를 처리할 핸들러를 지정.
					p.addLast(new DiscardServerHandler());
				}
			});
			
			// BootStrap 클래스의 bind()메서드로 접속할 port를 지정한다.
			ChannelFuture f = sb.bind(8888).sync();
			
			f.channel().closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			bossGrp.shutdownGracefully();
			workerGrp.shutdownGracefully();
		}
	}

}
