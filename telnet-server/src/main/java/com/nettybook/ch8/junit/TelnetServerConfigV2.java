package com.nettybook.ch8.junit;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@Configuration
/* 스프링 컨텍스트에서 사용할 클래스를 찾을 경로를 지정하는 데 사용한다. */
@ComponentScan("com.nettybook.ch8.junit")
@PropertySource("classpath:telnet-server.properties")
public class TelnetServerConfigV2 
{
	@Value("${boss.thread.count}")
	private int bossCnt;
	
	@Value("${worker.thread.count}")
	private int workerCnt;
	
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
	
	
	@Bean(name = "tcpSocketAddress")
	public InetSocketAddress tcpPort() {
		return new InetSocketAddress(tcpPort);
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConf() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
