package com.nettybook.ch8.junit;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class TelnetServerV3 
{
	@Autowired
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress addr;
	
	public void start() {
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.childHandler(new TelnetServerInitializerV3());
			
			ChannelFuture f = sb.bind(addr).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}
}
