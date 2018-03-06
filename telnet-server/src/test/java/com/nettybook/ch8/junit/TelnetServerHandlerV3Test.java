package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import org.junit.Test;

import io.netty.channel.embedded.EmbeddedChannel;

public class TelnetServerHandlerV3Test 
{
	@Test
	public void testConnect() {
		StringBuilder builder = new StringBuilder();
		
		try {
			builder.append("환영합니다. - ")
			.append(InetAddress.getLocalHost().getHostName())
			.append("에 접속하셨습니다!\r\n")
			.append("현재 시간은 ")
			/* 텔넷 서버에 접속했을 때 출력하는 메세지를 생성한다.
			 * 접속 시 출력하는 메세지에는 호스트명과 접속 시간이 포함된다. 이떄 서버의 접속 시간은 테스트할 때마다 변경되므로
			 * Date 객체를 사용하여 테스트를 요청할 때마다 날짜를 생성한다. */
			.append(new Date().toString())
			.append(" 입니다.\r\n");
		} catch (UnknownHostException e) {
			// TODO: handle exception
			fail();
			e.printStackTrace();
		}
		
		/* TelnetServerHandlerV3 클래스의 객체를 생성하여 EmbeddedChannel에 등록한다. */
		EmbeddedChannel embCh = new EmbeddedChannel(new TelnetServerHandlerV3());
		/* TelnetServerHandlerV3 클래스는 클라이언트가 접속할 때마다 환영 메세지를 전송한다.
		 * 인바운드 이벤트 핸들러의 channelActive 이벤트 메서드는 이벤트 핸들러가 EmbeddedChannel에 등록될 때 호출된다.
		 * 그러므로 다른 write 이벤트 메서드 호출 없이 readOutbound 메서드로 아웃바운드 데이터를 조회활 수 있다. */
		String expected = (String) embCh.readOutbound();
		/* readOutbound 메서드로 조회한 데이터가 존재하는지 확인한다. */
		assertNotNull(expected);
		
		/* 최초 StringBuilder객체에 기록된 데이터와 readOutboud 메서드로 조회한 데이터가 동일한지 확인한다. */
		assertEquals(builder.toString(), (String) expected);
		
		String req = "hello";
		/* 입력 메세지에 따라서 TelnetServerHandlerV3 핸들러의 응답 메세지가 달라지므로 일반 메세지인 hello를 입력했을 때 수신될 메세지를 생성한다. */
		expected = "입력하신 명령이 '" + req + "' 입니까?\r\n";
		
		/* hello 메세지를 writeOutbound 메서드로 EmbeddedChannel의 인바운드에 기록한다. */
		embCh.writeInbound(req);
		
		/* readOutbound 메서드를 사용하여 TelnetServerHandlerV3의 처리 결과를 조회한다. */
		String resp = (String) embCh.readOutbound();
		/* 조회한 메세지와 수신될 응답 메세지가 동일한지 확인한다. */
		assertEquals(expected, resp);
		
		embCh.finish();
	}
}
