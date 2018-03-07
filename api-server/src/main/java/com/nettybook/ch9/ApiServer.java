package com.nettybook.ch9;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

@ComponentScan
public final class ApiServer 
{
	@Autowired
	/* ApiServerConfig 클래스에서 프로퍼티 파일을 참조하여 생성된 InetSocketAddress 객체를 스프링의 Autowired 어노테이션을 사용하여 자동으로 할당한다.
	 * InetSocketAddress 객체는 API 서버의 서비스 포트인 8888번을 사용하며 이 값은 프로퍼티에 지정되어 있다.
	 * 즉 api-server.properties 파일의 tcp.port 값을 바꾸면 API 서버의 서비스 포트도 변경된다. */
	@Qualifier("tcpSocketAddr")
	private InetSocketAddress addr;
	
	@Autowired
	/* 프로퍼티의 worker.thread.count에 지정된 값을 ApiServer의 workerThreadCnt 필드에 자동으로 할당한다. */
	@Qualifier("workerThreadCnt")
	private int workerThreadCnt;
	
	@Autowired
	/* 프로퍼티의 boss.thread.count에 지정된 값을 ApiServer의 bossThreadCnt 필드에 자동으로 할당한다. */
	@Qualifier("bossThreadCnt")
	private int bossThreadCnt;
	
	public void start() {
		EventLoopGroup boss = new NioEventLoopGroup(bossThreadCnt);
		EventLoopGroup worker = new NioEventLoopGroup(workerThreadCnt);
		ChannelFuture chFuture = null;
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			
			sb.group(boss, worker).channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			/* API 서버의 채널 파이프라인 설정 클래스를 지정한다.
			 * ApiServerInitializer의 인자는 SSL 컨텍스트이며, 지금은 일반 HTTP를 다루므로 null로 설정했다. */
			.childHandler(new ApiServerInitializer(null));
			
			Channel ch = sb.bind(addr).sync().channel();
			
			chFuture = ch.closeFuture();
			/* 서버 채널의 closeFuture 객체를 가져와서 채널 닫힘 이벤트가 발생할 때까지 대기한다.
			 * 즉 서버의 메인 스레드는 이 부분에서 멈춘다. */
			chFuture.sync();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// TODO: handle finally clause
			worker.shutdownGracefully();
			boss.shutdownGracefully();
		}
		
		// private ChannelFuture initializeServerNetworkBySSL(EventLoopGroup
	    // bossGroup, EventLoopGroup workerGroup, int listenPort) throws
	    // InterruptedException {
	    // SslContext sslCtx = null;
	    //
	    // try {
	    // File certChainFile =
	    // ConfigReader.getInstance().getConfigFile(CoreConstantsName.SSL_PUBLIC_KEY);
	    // File keyFile =
	    // ConfigReader.getInstance().getConfigFile(CoreConstantsName.SSL_PRIVATE_KEY);
	    //
	    // sslCtx = SslContext.newServerContext(certChainFile, keyFile, null);
	    // }
	    // catch (SSLException | FileNotFoundException e) {
	    // logger.error(e);
	    // }
	    //
	    // ServerBootstrap b = new ServerBootstrap();
	    // b.group(bossGroup, workerGroup)
	    // .channel(NioServerSocketChannel.class)
	    // .handler(new LoggingHandler(LogLevel.INFO))
	    // .childHandler(new BigbrotherServerInitializer(sslCtx));
	    //
	    // Channel ch = b.bind(listenPort + 1000).sync().channel();
	    //
	    // ChannelFuture channelFuture = null;
	    // channelFuture = ch.closeFuture();
	    //
	    // logger.info(DisplayForLog.makeFooter());
	    //
	    // return channelFuture;
	    // }
	}
}
