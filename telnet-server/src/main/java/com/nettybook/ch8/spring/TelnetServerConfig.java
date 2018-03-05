package com.nettybook.ch8.spring;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/* Configuration 어노테이션은 지정된 클래스가 스프링의 설정 정보를 포함한 클래스임을 표시한다.
 * TelnetServerConfig 클래스에는 Bean 설정 정보와, ComponentScan, PropertySource 정보가 포함된다. */
@Configuration
/* ComponentScan 어노테이션은 스프링의 컨텍스트가 클래스를 동적으로 찾을 수 있도록 한다는 의미이며,
 * 입력되는 패키지명(com.nettybook.ch8.spring)을 포함한 하위 패키지를 대상으로 검색한다는 의미이다. */
@ComponentScan("com.nettybook.ch8.spring")
/* PropertySource 어노테이션은 설정 정보를 가진 파일의 위치에서 파일을 읽어서 Enviroment 객체로 자동 저장된다.
 * 설정 파일의 이름은 telnet-server.properties 이며 클래스 패스에서 설정파일을 검색한다. */
@PropertySource("classpath:telnet-server.properties")
public class TelnetServerConfig 
{
	/* PropertySource 어노테이션에서 읽어들인 설정 정보에서 boss.tread.count 키로 지정된 설정 값을 찾고 해당 값을 변수 bossCnt에 할당한다. */
	@Value("${boss.thread.count}")
	private int bossCnt;
	
	/* PropertySource 어노테이션에서 읽어들인 설정 정보에서 worker.thread.count 키로 지정된 설정 값을 찾고 해당 값을  변수 workerCnt에 할당한다. */
	@Value("${worker.thread.count}")
	private int workerCnt;
	/* PropertySource 어노테이션에서 읽어들인 설정 정보에서 tcp.port 키로 지정된 설정 값을 찾고 해당 값을 변수 tcpPort에 할당한다. */
	@Value("${tcp.port}")
	private int tcpPort;

	public int getBossCnt() {
		return bossCnt;
	}

	public void setBossCnt(int bossCnt) {
		this.bossCnt = bossCnt;
	}

	public int getWorkerCnt() {
		return workerCnt;
	}

	public void setWorkerCnt(int workerCnt) {
		this.workerCnt = workerCnt;
	}

	public int getTcpPort() {
		return tcpPort;
	}

	public void setTcpPort(int tcpPort) {
		this.tcpPort = tcpPort;
	}
	
	/* 설정 파일에서 읽어들인 tcp.port 정보로부터 InetSocketAddress 객체를 생성하고 객체이름을 tcpSocketAddress로 지정한다.
	 * 이 설정은 스프링 컨텍스트에 tcpSocketAddress라는 이름으로 추가되며 다른 Bean에서도 사용할 수 있다. */
	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	/* PropertySource 어노테이션에서 사용할 Enviroment 객체를 생성하는 Bean을 생성한다. */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConf() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
