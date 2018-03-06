package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Test;

import io.netty.channel.embedded.EmbeddedChannel;

public class TelnetServerHandlerTest 
{
	@Test
	public void testConnect() throws UnknownHostException {
		String fResp = "Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n";
		
		EmbeddedChannel ch = new EmbeddedChannel(new TelnetServerHandlerNetty());
		String resp = (String) ch.readOutbound();
		
		assertNotNull(resp);
		assertEquals(fResp, (String) resp);	// 기대한 첫 번째 응답
		
		resp = (String) ch.readOutbound();
		assertNotNull(resp);
		assertEquals("It is " + new Date() + " now.\r\n", resp);
		
		ch.writeInbound("test");
		ch.writeInbound("\r\n");
		
		String inbound = (String) ch.readOutbound();
		assertEquals("Did you say 'test'?\r\n", inbound);
		
		// 채널 상대측에 메세지 전송
		// assertTrue(ch.writeInbound(Unpooled.wrappedBuffer("test".getBytes(Charset.forName("UTF-8")))));
		// 채널 상대측에 메세지 전송
		// assertTrue(ch.writeInbound(Unpooled.wrappedBuffer("\r\n".getBytes(Charset.forName("UTF-8")))));
		
		// TelnetServerHandlerNetty의 인바운드 데이터.
		// ByteBuf inboundBuf = (ByteBuf) ch.readInbound();
		// assertEquals("test\r\n", inboundBuf.toString(Charset.forName("UTF-8")));
		
		String outboundBuf = (String) ch.readOutbound();
		assertNotNull(outboundBuf);	// null
		// assertEquals(resp, (String) outboundBuf);
		
	}
}
