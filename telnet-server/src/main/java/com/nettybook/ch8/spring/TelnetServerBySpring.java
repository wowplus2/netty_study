package com.nettybook.ch8.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


public class TelnetServerBySpring 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/* 어노테이션 기반의 스프링 컨텍스트 객체를 생성한다. */
		AbstractApplicationContext springCtx = null;
		
		try {
			/* 어노테이션 설정을 가진 클래스를 지정한다.
			 * 여기에 입력되는 클래스는 스프링 컨텍스트를 생성하는 데 필요한 설정 정보가 포함되어 있다. */
			springCtx = new AnnotationConfigApplicationContext(TelnetServerConfig.class);
			springCtx.registerShutdownHook();
			
			/* 스프링 컨텍스트에서 TelnetServerV2 클래스의 객체를 가져온다. */
			TelnetServerV2 svr = springCtx.getBean(TelnetServerV2.class);
			/* 텔넷 서버의 start 메서드를 실행한다. */
			svr.start();
		} finally {
			// TODO: handle finally clause
			springCtx.close();
		}
	}

}
