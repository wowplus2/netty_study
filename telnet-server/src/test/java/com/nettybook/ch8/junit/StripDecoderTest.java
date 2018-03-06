package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;

public class StripDecoderTest 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//
	}
	
	@Before
	public void setUp() throws Exception {
		//
	}
	
	@After
	public void tearDown() throws Exception {
		//
	}
	
	@Test
	public void test() {
		String data = "test";
		EmbeddedChannel ch = new EmbeddedChannel(new StripDecoder());
		
		ByteBuf req = Unpooled.wrappedBuffer(data.getBytes());
		ch.writeInbound(req);
		
		ByteBuf resp = (ByteBuf) ch.readOutbound();
		
		assertEquals("a" + data + "a", resp.toString(Charset.defaultCharset()));
		
		ch.finish();
	}
}
