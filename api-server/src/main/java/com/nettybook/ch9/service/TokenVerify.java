package com.nettybook.ch9.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nettybook.ch9.core.ApiRequestTemplate;
import com.nettybook.ch9.core.JedisHelper;

import redis.clients.jedis.Jedis;


/* 스프링의 Service 어노테이션은 스프링 컨텍스트가 TokenVerify 클래스를 생성할 수 있도록한다.
 * 문자열로 지정된 tokenVerify는 스프링 컨텍스트에서 클래스의 객체를 생성할 때 사용할 이름이다.
 * 즉 getBean 메서드 호출의 인자로 사용된다. */
@Service("tokenVerify")
@Scope("prototype")
public class TokenVerify extends ApiRequestTemplate 
{
	private static final JedisHelper helper = JedisHelper.getInstance();

	public TokenVerify(Map<String, String> reqData) {
		super(reqData);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void requestParamValidation() throws RequestParamException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(this.reqData.get("token"))) {
			throw new RequestParamException("명령수행 대상값이 없습니다.");
		}
	}

	@Override
	public void service() throws ServiceException {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		
		try {
			jedis = helper.getConnection();
			/* 제디스를 사용하여 레디스의 get 명령을 수행한다.
			 * get 명령에 입력되는 인자는 만료하고자 하는 토큰 정보이다. */
			String strToken = jedis.get(this.reqData.get("token"));
			
			if (strToken == null) {
				this.apiResult.addProperty("res_cd", "404");
				this.apiResult.addProperty("message", "fail");				
			}
			else {
				Gson gson = new Gson();
				JsonObject token = gson.fromJson(strToken, JsonObject.class);
				
				// helper.
				this.apiResult.addProperty("res_cd", "200");
				this.apiResult.addProperty("message", "success");
				this.apiResult.add("email", token.get("email"));
				this.apiResult.add("uidx", token.get("uidx"));
				this.apiResult.add("issue_date", token.get("issueDate"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			helper.returnResource(jedis);
		}
	}

}
