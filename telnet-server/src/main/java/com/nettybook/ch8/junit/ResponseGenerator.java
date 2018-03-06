package com.nettybook.ch8.junit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class ResponseGenerator 
{
	private String req;
	
	/* 입력된 문자열을 인수로 */
	public ResponseGenerator(String req) {
		this.req = req;
	}
	/* 파이프라인에 저장된 버퍼를 전송하는 flush 메서드를 호출한다. */
	public final String response() {
		String cmd = null;
		
		if (this.req.isEmpty()) {
			cmd = "명령을 입력해 주세요.\r\n";
		} else if ("bye".equals(this.req.toLowerCase())) {
			cmd = "Have a good day!\r\n";
		} else {
			cmd = "입력하신 명령이 '" + req + "' 입니까?\r\n";
		}
		
		return cmd;
	}
	
	public boolean isClose() {
		return "bye".equals(this.req);
	}
	
	public static final String makeGreetingMessage() throws UnknownHostException {
		StringBuilder sb = new StringBuilder();
		
		sb.append("환영합니다. - ")
        .append(InetAddress.getLocalHost().getHostName())
        .append("에 접속하셨습니다!\r\n")
        .append("현재 시간은 ").append(new Date().toString())
        .append(" 입니다.\r\n");
		
		return sb.toString();
	}
}
