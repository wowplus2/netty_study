package com.nettybook.ch8;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetServerInitializer extends ChannelInitializer<SocketChannel> 
{
	/* 네티가 제공하는 문자열 디코더인 StringDecoder 객체를 생성하고 전역 상수에 할당했다. */
	private static final StringDecoder DECODER = new StringDecoder();
	/* 네티가 제공하는 문자열 인코더인 StringEncoder 객체를 생성하고 전역 상수에 할당했다. */
	private static final StringEncoder ENCODER = new StringEncoder();
	/* 텔넷 서버의 업무를 처리하기 위한 TelnetServerHandler 객체를 생성하고 전역 상수에 할당했다. */
	private static final TelnetServerHandler SVR_HANDLER = new TelnetServerHandler();
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline pipeline = ch.pipeline();
		
		/* 네티가 제공하는 구분자 기반의 디코더인 DelimiterBaseFrameDecoder 객체를 생성하고 채널 파이프라인의 맨 앞에 등록했다. */
		pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		/* 전역 상수에 등록된 StringDecoder를 채널 파이프라인의 두번쨰 데이터 핸들러로 등록했다. */
		pipeline.addLast(DECODER);
		/* 전역 상수에 등록된 StringEncoder를 채널 파이프라인의 세번째 데이터 핸들러로 등록했다. */
		pipeline.addLast(ENCODER);
		/* 전역 상수에 등록된 TelnetServerHandler를 채널 파이프라인의 네번째 데이터 핸들러로 등록했다. */
		pipeline.addLast(SVR_HANDLER);
	}
}
