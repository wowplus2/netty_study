package com.nettybook.ch9.service;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nettybook.ch9.core.ApiRequestTemplate;
import com.nettybook.ch9.core.JedisHelper;

import redis.clients.jedis.Jedis;


/* 스프링 Service 어노테이션은 스프링 컨텍스트가 TokenExpier 클래스를 생성할 수 있도록 한다.
 * 문자열로 지정된 tokenExpier는 스프링 컨텍스트에서 클래스의 객체를 생성할 떄 사용할 이름이다.
 * 즉 스프링의 컨텍스트의 getBean 메서드 호출의 인자로 사용된다. */
@Service("tokenExpier")
@Scope("prototype")
public class TokenExpier extends ApiRequestTemplate 
{
	private static final JedisHelper helper = JedisHelper.getInstance();
	
	public TokenExpier(Map<String, String> reqData) {
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
			// token 저장
			/* 제디스로부터 새로운 연결을 가져온다. */
			jedis = helper.getConnection();
			long result = jedis.del(this.reqData.get("token"));
			System.out.println(result);
			
			// helper.
			this.apiResult.addProperty("res_cd", "200");
			this.apiResult.addProperty("message", "success");
			this.apiResult.addProperty("token", this.reqData.get("token"));
		} catch (Exception e) {
			// TODO: handle exception
			helper.returnResource(jedis);
		}
	}

}
