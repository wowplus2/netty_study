package com.nettybook.ch7.telnetserver;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> 
{
	/* 네티에서 제공하는 문자열 디코더인 StringDecoder를 static final로 생성한다.
	 * 즉 이 디코더는 모튼 채널 파이프라인에서 공유된다. */
	private static final StringDecoder DECODER = new StringDecoder();
	/* 네티에서 제공하는 문자열 인코더인 StringEncoder를 static final로 생성한다.
	 * 디코더와 마찬가지로 이 인코더는 모든 채널 파이프라인에서 공유된다. */
	private static final StringEncoder ENCODER = new StringEncoder();
	
	/* 텔넷 서버가 제공하는 기능을 구현한 로직이 포함된 TelnetServerHandler를 static final로 생성한다.
	 * 위의 두 코덱과 마찬가지로 모든 채널 파이프라인에서 공유된다. */
	private static final TelnetServerHandler SERVER_HANDLER = new TelnetServerHandler();
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = ch.pipeline();
		
		/* DelimiterBasedFrameDecoder는 네티가 제공하는 기본 디코더로서 구분자 기반의 패킷을 처리한다. */
		p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		p.addLast(DECODER);
		p.addLast(ENCODER);
		p.addLast(SERVER_HANDLER);
	}
}
