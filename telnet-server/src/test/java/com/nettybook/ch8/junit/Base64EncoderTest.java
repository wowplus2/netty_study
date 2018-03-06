package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.base64.Base64Encoder;

public class Base64EncoderTest 
{
	@Test
	public void testEncoder() {
		String wData = "안녕하세요";
		ByteBuf req = Unpooled.wrappedBuffer(wData.getBytes());
		
		/* 테스트를 위한 Base64Encoder 객체를 생성한다. */
		Base64Encoder encoder = new Base64Encoder();
		/* EmbeddedChannel에 Base64Encoder 객체를 등록한다. */
		EmbeddedChannel embeddedCh = new EmbeddedChannel(encoder);
		
		/* writeOutbound 메서드로 EmbeddedChannel의 아웃바운드에 데이터를 기록한다. */
		embeddedCh.writeOutbound(req);
		/* readOutbound 메서드로 Base64Encoder의 인코딩 결과를 조회한다. */
		ByteBuf resp = (ByteBuf) embeddedCh.readOutbound();
		
		/* '안녕하세요'라는 문자열을 Base64로 인코딩한 값으로 선언한다. */
		String expect = "7JWI64WV7ZWY7IS47JqU";
		/* 선언한 값과 readOutbound 메서드로 조회한 값이 동일한지 확인한다. */
		assertEquals(expect, resp.toString(Charset.defaultCharset()));
		
		embeddedCh.finish();
	}
}
