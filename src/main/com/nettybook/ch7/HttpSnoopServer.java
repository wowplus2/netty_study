package com.nettybook.ch7;

import java.io.File;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;

public class HttpSnoopServer 
{
	/* SSL/TLS 연결을 수락하기 위한 포트를 8443번으로 변경했다. 
	 * SSL/TLS 연결을 위한 기본포트는 443번이다. */
	private static final int PORT = 8443;
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SslContext sslCtx = null;
		
		try {
			/* 생성한 인증서 파일을 지정한다. */
			File certChainFile = new File("nettybook.crt");
			/* 생성한 개인키 파일을 지정한다. */
			File keyFile = new File("wowpluskey.pem");
			
			/* 인증서와 개인키를 사용하여 SslContext 객체를 생성한다. 마지막 인수는 개인키 생성 시 입력한 비밀번호 */
			/* 참고: http://netty.io/wiki/sslcontextbuilder-and-private-key.html */
			sslCtx = SslContext.newServerContext(certChainFile, keyFile, "wl○○○○○14");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		EventLoopGroup boss = new NioEventLoopGroup(1);
		EventLoopGroup worker = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(boss, worker)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler())
			/* SslContext 객체를 채널 파이프라인을 생성할 때 사용하므로 HttpSnoopServerInitializer의 생성자를 참조로 넘겨준다. */
			.childHandler(new HttpSnoopServerInitializer(sslCtx));
			
			Channel ch = sb.bind(PORT).sync().channel();
			
			ch.closeFuture().sync();
		} finally {
			// TODO: handle finally clause
			boss.shutdownGracefully();
			worker.shutdownGracefully();
		}
	}

}
