package com.nettybook.ch9.core;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/* Service class dispatcher by uri */
@Component
public class ServiceDispatcher 
{
	/* 정적변수에 스프링 컨텍스트를 할당한다. */
	private static ApplicationContext springCtx;
	
	@Autowired
	/* 스프링 컨텍스트는 정적 변수에 직접 할당할 수 없기 떄문에 메서드에 Autowired 어노테이션을 사용하여 간접적으로 할당한다. */
	public void init(ApplicationContext springCtx) {
		ServiceDispatcher.springCtx = springCtx;
	}
	
	protected Logger logger = LogManager.getLogger(this.getClass());
	
	/* HTTP 요청에서 추출한 값을 가진 맵 객체를 인수로 하여 dispatch 메서드를 선언한다. */
	public static ApiRequest dispatch(Map<String, String> requestMap) {
		/* HTTP 요청의 URI값을 확인한다. 이값을 기준으로 API 서비스 클래스를 생성한다. */
		String serviceUri = requestMap.get("REQUEST_URI");
		String beanName = null;
		
		/* URI 값이 없으면 beanName에 기본값으로 notFound를 설정한다. */
		if (serviceUri == null) {
			beanName = "notFound";
		}
		
		/* HTTP 요청 URI가 /tokens로 시작하면 beanName에 토큰을 처리하는 API 서비스 클래스 중에서 하나를 선택한다. */
		if (serviceUri.startsWith("/tokens")) {
			/* HTTP 요청에서 메서드타입을 확인한다. */
			String httpMethod = requestMap.get("REQUEST_METHOD");
			
			switch (httpMethod) {
			case "POST":
				beanName = "tokenIssue";
				break;
			case "DELETE":
				beanName = "tokenExpier";
				break;
			case "GET":
				beanName = "toeknVerify";
				break;
			default :
				beanName = "notFound";
				break;
			}
		} 
		/* HTTP 요청 URI가 /users로 시작하면 beanName에 사용자번호 조회 API를 할당한다. */
		else if (serviceUri.startsWith("/users")) {
			beanName = "users";
		}
		else {
			beanName = "notFound";
		}
		
		ApiRequest service = null;
		try {
			/* beanName값을 사용하여 스프링 컨텍스트에서 API 서비스 클래스 객체를 생성한다.
			 * 다른 오류 없이 이 부분까지 도달했다면 beanName에는 설계시 할당된 API 또는 notFound 문자열이 설정되어 있다. */
			service = (ApiRequest) springCtx.getBean(beanName, requestMap);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			/* 스프링 컨텍스트에서 API 서비스 클래스를 생성하는 중에 오류가 발생하면 기본 API 서비스 클래스를 생성한다. */
			service = (ApiRequest) springCtx.getBean("notFound", requestMap);
		}
		
		return service;
	}
	
}
