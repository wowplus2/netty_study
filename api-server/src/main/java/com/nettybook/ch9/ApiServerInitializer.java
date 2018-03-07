package com.nettybook.ch9;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslContext;

public class ApiServerInitializer extends ChannelInitializer<SocketChannel> 
{
	private final SslContext sslCtx;
	
	public ApiServerInitializer(SslContext sslCtx) {
		// TODO Auto-generated constructor stub
		this.sslCtx = sslCtx;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = ch.pipeline();
		
		if (sslCtx != null) {
			p.addLast(sslCtx.newHandler(ch.alloc()));
		}
		
		p.addLast(new HttpRequestDecoder());
		p.addLast(new HttpObjectAggregator(65536));
		p.addLast(new HttpResponseEncoder());
		p.addLast(new HttpContentCompressor());
		p.addLast(new ApiRequestParser());
	}
}
