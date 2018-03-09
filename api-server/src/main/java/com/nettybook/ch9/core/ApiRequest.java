package com.nettybook.ch9.core;

import com.google.gson.JsonObject;
import com.nettybook.ch9.service.RequestParamException;
import com.nettybook.ch9.service.ServiceException;

public interface ApiRequest 
{
	/* Request param null check method. 
	 * API를 호출하는 HTTP 요청의 파라미터 값이 입력되어있는지 검증하는 메서드 */
	public void requestParamValidation() throws RequestParamException;
	
	/* 서비스 구현
	 * 각 API 서비스에 따른 개별 구현 메서드 */
	public void service() throws ServiceException;
	
	/* API 서비스 호출 시 실행 
	 * 서비스 API의 호출 시작 메서드 */
	public void executeService();
	
	/* API 서비스 수행 결과 조회 
	 * API 서비스의 처리 결과를 조회하는 메서드 */
	public JsonObject getApiResult();
}
