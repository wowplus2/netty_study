package com.nettybook.ch9.core;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.nettybook.ch9.service.RequestParamException;
import com.nettybook.ch9.service.ServiceException;

public abstract class ApiRequestTemplate implements ApiRequest 
{
	protected Logger logger;
	
	/* API 요청 data */
	protected Map<String, String> reqData;
	
	/* API 처리결과 */
	protected JsonObject apiResult;
	
	/* apiResult 객체 생성 
	 * HTTP 요청에서 추출한 필드의 이름과 값을 API 서비스 클래스의 생성자로 전달한다. */
	public ApiRequestTemplate(Map<String, String> reqData) {
		this.logger = LogManager.getLogger(this.getClass());
		this.apiResult = new JsonObject();
		this.reqData = reqData;
		
		logger.info("request data : " + this.reqData);
	}
	
	public void executeService() {
		// TODO Auto-generated method stub
		try {
			/* executeService 메서드에서 아직 구현하지 않은 requestParamValidation 메서드를 호출한다.
			 * requestParamValidation 메서드는 API 서비스 클래스의 인수로 입력된 HTTP 요청 맵의 정합성을 검증한다.
			 * 이 메서드는 ApiRequestTemplate 추상 클래스를 상속받아 클래스에서 구현해야 한다. */
			this.requestParamValidation();
			
			/* executeService 메서드에서 아직 구현하지 않은 service 메서드를 호출한다.
			 * service 메서드는 각 API 서비스클래스가 제공할 기능을 구현해야 한다.
			 * 이 메서드는 ApiRequestTemplate 추상 클래스를 상속받은 클래스에서 구현하여야 한다. */
			this.service();
		} catch (RequestParamException e) {
			// TODO: handle exception
			logger.error(e);
			this.apiResult.addProperty("res_cd", "405");
		} catch (ServiceException e) {
			logger.error(e);
			this.apiResult.addProperty("res_cd", "501");
		}
	}

	public JsonObject getApiResult() {
		// TODO Auto-generated method stub
		/* executeService 메서드가 호출된 이후에 service 메서드에서 할당한 API 처리 결과를 돌려준다. */
		return this.apiResult;
	}

	public void requestParamValidation() throws RequestParamException {
		// TODO Auto-generated method stub
		if (getClass().getClasses().length == 0) {
			return;
		}
		
		// // TODO 이건 꼼수 바꿔야 하는데..
        // for (Object item :
        // this.getClass().getClasses()[0].getEnumConstants()) {
        // RequestParam param = (RequestParam) item;
        // if (param.isMandatory() && this.reqData.get(param.toString()) ==
        // null) {
        // throw new RequestParamException(item.toString() +
        // " is not present in request param.");
        // }
        // }
	}
	
	public final <T extends Enum<T>> T fromValue(Class<T> paramClass, String paramValue) throws IllegalAccessException {
		if (paramValue == null || paramClass == null) {
			throw new IllegalArgumentException("There is no value with name '" + paramValue + "'' in Enum " + paramClass.getClass().getName());
		}
		
		for (T param : paramClass.getEnumConstants()) {
			if (paramValue.equals(param.toString())) {
				return param;
			}
		}
		
		throw new IllegalAccessException("There is no value with name '" + paramValue + "' in Enum " + paramClass.getClass().getName());
	}
}
