package com.nettybook.ch9;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ApiServerMain 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AbstractApplicationContext springCtx = null;
		
		try {
			springCtx = new AnnotationConfigApplicationContext(ApiServerConfig.class);
			springCtx.registerShutdownHook();
			
			ApiServer svr = springCtx.getBean(ApiServer.class);
			svr.start();
		} finally {
			// TODO: handle finally clause
			springCtx.close();
		}
	}

}
