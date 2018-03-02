package com.nettybook.ch7;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

public class HttpSnoopServerInitializer extends ChannelInitializer<SocketChannel> 
{
	private final SslContext sslCtx;
	
	public HttpSnoopServerInitializer(SslContext sslCtx) {
		// TODO Auto-generated constructor stub
		/* HttpSnoopServer 클래스에서 넘겨받은 SslContext의 객체를 멤버변수에 할당한다. */
		this.sslCtx = sslCtx;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = ch.pipeline();
		
		if (sslCtx != null) {
			/* 새로운 채널이 생성될 떄마다 SslHandler를 넘겨준다. */
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		p.addLast(new HttpRequestDecoder());
		p.addLast(new HttpObjectAggregator(1048576));
		p.addLast(new HttpResponseEncoder());
		p.addLast(new HttpSnoopServerHandler());
	}
}
