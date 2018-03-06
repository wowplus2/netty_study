package com.nettybook.ch8.junit;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class TelnetServerBySpringV2 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractApplicationContext springCtx = null;
		
		try {
			springCtx = new AnnotationConfigApplicationContext(TelnetServerConfigV2.class);
			springCtx.registerShutdownHook();
			
			TelnetServerV3 srv = springCtx.getBean(TelnetServerV3.class);
			srv.start();
		} finally {
			// TODO: handle finally clause
			springCtx.close();
		}
	}

}
