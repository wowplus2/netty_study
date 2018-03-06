package com.nettybook.ch8.junit;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class TelnetServerInitializerV3 extends ChannelInitializer<SocketChannel>
{
	private static final StringDecoder _DECODER = new StringDecoder();
	private static final StringEncoder _ENCODER = new StringEncoder();
	private static final TelnetServerHandlerV3 _SRV_HANDLER = new TelnetServerHandlerV3();
	
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = ch.pipeline();
		
		// Add the text line codec combination first,
		p.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
		// the encoder and decoder are static as these are sharable
		p.addLast(_DECODER);
		p.addLast(_ENCODER);		
		// and then business logic.
		p.addLast(_SRV_HANDLER);
	}
}
