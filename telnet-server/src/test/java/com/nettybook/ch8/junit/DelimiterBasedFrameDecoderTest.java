package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;

public class DelimiterBasedFrameDecoderTest 
{
	@Test
	public void testDecoder() {
		String wData = "안녕하세요\r\n반갑습니다\r\n";
		String fResponse = "안녕하세요\r\n";
		String sResponse = "반갑습니다\r\n";
		
		/* 최대 8192바이트의 데이터를 줄바꿈 문자를 기준으로 잘라서 디코딩하는 DelimiterBasedFrameDecoder 객체를 생성한다.
		 * 디코더의 두 번째 인수는 디코딩된 데이터에 구분자의 포함 여부를 설정한다.
		 * 이 예제에서는 false로 설정하였으므로 디코딩된 문자열에 줄바꿈 문자가 포함되지 않는다. */
		DelimiterBasedFrameDecoder decoder = new DelimiterBasedFrameDecoder(8192, false, Delimiters.lineDelimiter());
		/* EmbeddedChannel에 위에서 생성한 DelimiterBaseFrameDecoder 객체를 등록한다. */
		EmbeddedChannel embeddedChannel = new EmbeddedChannel(decoder);
		
		/* '안녕하세요\r\n반갑습니다' 라는 문자열을 네티의 바이트 버퍼로 변환한다. */
		ByteBuf req = Unpooled.wrappedBuffer(wData.getBytes());
		/* 바이트 버퍼로 변환된 문자열을 EmbeddedChannel의 인바운드에 기록한다.
		 * 즉 클라이언트로부터 데이터를 수신한 것과 같은 상태가 된다. */
		boolean res = embeddedChannel.writeInbound(req);
		/* writeInbound 메서드의 수행 결과가 정상인지 확인한다.
		 * writeInbound 메서드의 수행 결과는 EmbeddedChannel의 버퍼에 데이터가 정상적으로 기록되었음을 나타낸다. */
		assertTrue(res);
		
		ByteBuf resp = null;
		
		/* EmbeddedChannel에서 인바운드 데이터를 읽는다.
		 * 즉 디코더가 수신하여 디코딩한 데이터를 조회한다. 
		 * DelimiterBasedFrameDecoder는 줄바꿈 문자를 기준으로 데이터를 분리하므로 wData문자열의 시작부터 첫 번째 줄바꿈 문자 앞 문자열인 '안녕하세요\r\n'을 돌려준다. */
		resp = (ByteBuf) embeddedChannel.readInbound();
		/* 위에서 돌려받은 문자열이 '안녕하세요\r\n'과 동일한지 확인한다. */
		assertEquals(fResponse, resp.toString(Charset.defaultCharset()));
		
		/* EmbeddedChannel에서 인바운드 데이터를 읽는다.
		 * 첫 번째 줄바꿈 문자 뒤 문자열인 '반갑습니다\r\n'를 돌려준다. */
		resp = (ByteBuf) embeddedChannel.readInbound();
		/* 위에서 돌려받은 문자열이 '반갑습니다\r\n'과 동일한지 확인한다. */
		assertEquals(sResponse, resp.toString(Charset.defaultCharset()));
		
		/* EmbeddedChannel을 종료한다.
		 * finish 메서드가 호출된 이후에는 EmbeddedChannel에 어떤 데이터도 기록할 수 없다. */
		embeddedChannel.finish();
	}
}
