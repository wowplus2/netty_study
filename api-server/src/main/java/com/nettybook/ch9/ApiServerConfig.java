package com.nettybook.ch9;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("classptah:spring/hsqlApplicationContext.xml")
/* ComponentScan 어노테이션은 스프링이 컴포넌트를 검색할 위치를 지정하는데 
 * 여기서는 ApiServer 클래스가 위치한 com.nettybook.ch9 패키지와 
 * 토큰발급 및 사용자 정보조회 클래스가 위치한 com.nettybook.ch9.service 패키지를 지정하였다. */
@ComponentScan("com.nettybook.ch9.core, com.nettybook.ch9, com.nettybook.ch9.service")
/* API 서버의 설정 프로퍼티 파일인 api-server.properties의 위치를 지정했다. */
@PropertySource("classpath:api-server.properties")
public class ApiServerConfig 
{
	@Value("${boss.thread.count}")
	private int bossThreadCnt;
	
	@Value("${worker.thread.count}")
	private int workerThreadCnt;
	
	@Value("${tcp.port}")
	private int tcpPort;

	/* 프로퍼티에서 읽어들인 boss.thread.count 값을 Bean 어노테이션을 사용하여 다른 클래스에서
	 * 참조할 수 있도록 설정했다. 이 값은 ApiServer 클래스의 부트스트랩에서 사용된다. */
	@Bean(name = "bossThreadCnt")
	public int getBossThreadCnt() {
		return bossThreadCnt;
	}

	/* 프로퍼티에서 읽어들인 worker.thread.count 값을 Bean 어노테이션을 사용하여 다른 클래스에서 
	 * 참조할 수있도록 설정했다. 이 값은 ApiServer 클래스의 부트스트랩에서 사용된다. */
	@Bean(name = "workerThreadCnt")
	public int getWorkerThreadCnt() {
		return workerThreadCnt;
	}

	public int getTcpPort() {
		return tcpPort;
	}
	
	@Bean(name = "tcpSocketAddr")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConf() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	
}
