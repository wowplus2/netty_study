package com.nettybook.ch8.spring;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nettybook.ch8.TelnetServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/* 클래스에 Component 어노테이션을 지정하여 TelnetServerV2 클래스의 객체가 스프링 컨텍스트에 등록되게 한다. */
@Component
public class TelnetServerV2 
{
	/* TelnetServerV2 클래스의 port 필드값이 자동 할당되도록 지정한다. */
	@Autowired
	/* 스프링 컨텍스트에 지정된 객체 이름 중에 tcpSocketAddress에 해당하는 객체를 할당하도록 지정한다. */
	@Qualifier("tcpSocketAddress")
	private InetSocketAddress addr;
	
	public void start() {
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.childHandler(new TelnetServerInitializer());
			
			/* TelnetServerConfig 클래스에서 설정한 객체인 tcpSocketAddress를 사용하여 서버포트를 바인드한다. */
			ChannelFuture f = sb.bind(addr).sync();
			
			f.channel().closeFuture().sync();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

}
