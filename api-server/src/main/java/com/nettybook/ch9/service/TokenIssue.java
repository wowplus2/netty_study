package com.nettybook.ch9.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.gson.JsonObject;
import com.nettybook.ch9.core.ApiRequestTemplate;
import com.nettybook.ch9.core.JedisHelper;
import com.nettybook.ch9.core.KeyMaker;
import com.nettybook.ch9.service.dao.TokenKey;

import redis.clients.jedis.Jedis;

/* 스프링의 Service 어노테이션은 스프링 컨텍스트가 TokenIssue 클래스를 생성할 수 있도록 하며 
 * 문자열로 지정된 tokenIssue는 스프링 컨텍스트에서 클래스의 객체를 생성할 때 사용할 이름이다.
 * 즉 getBean 메서드 호출의 인자로 사용된다. */
@Service("tokenIssue")
@Scope("prototype")
public class TokenIssue extends ApiRequestTemplate 
{
	/* 레디스에 접근하기 위한 제디스 핼퍼 클래스이다. */
	private static final JedisHelper helper = JedisHelper.getInstance();
	
	@Autowired
	private SqlSession sqlSession;

	public TokenIssue(Map<String, String> reqData) {
		super(reqData);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	/* requestParamValidation 메서드에서 인증 토큰 발급 API의 입력 파라메터를 검증한다.
	 * 검증하는 필드명은 usridx와 password이다. */
	public void requestParamValidation() throws RequestParamException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(this.reqData.get("usridx"))) {
			throw new RequestParamException("사용자 인덱스가 전달되지 않았습니다.");
		}
		
		if (StringUtils.isEmpty(this.reqData.get("password"))) {
			throw new RequestParamException("사용자 비밀번호가 전달되지 않았습니다.");
		}
	}

	@Override
	public void service() throws ServiceException {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		
		try {
			Map<String, Object> result = sqlSession.selectOne("users.userInfoByPassword", this.reqData);
			
			if (result != null) {
				final long threeHour = 60 * 60 * 3;
				long issueDate = System.currentTimeMillis() / 1000;
				String email = String.valueOf(result.get("USRID"));
				
				// token 만들기
				JsonObject token = new JsonObject();
				token.addProperty("issue_date", issueDate);
				token.addProperty("exprie_date", issueDate + threeHour);
				token.addProperty("email", email);
				token.addProperty("uidx", reqData.get("usridx"));
				
				// token 저장
				/* 발급됨 토큰을 레디스에 저장하고 wh회하고자 KeyMaker 인터페이스를 사용했다. */
				KeyMaker tokenKey = new TokenKey(email, issueDate);
				jedis = helper.getConnection();
				/* 제디스를 사용하여 레디스의 setex 명령을 수행했다. 
				 * setex 명령은 지정된 시간 이후에 데이터를 자동으로 삭제하는 명령이다. */
				jedis.setex(tokenKey.getKey(), (int) threeHour, token.toString());
				
				// helper
				this.apiResult.addProperty("res_cd", "200");
				this.apiResult.addProperty("message", "success");
				this.apiResult.addProperty("token", tokenKey.getKey());
			}
			else {
				// 데이터 없음.
				this.apiResult.addProperty("res_cd", "404");
				this.apiResult.addProperty("message", "fail");
			}
		} catch (Exception e) {
			// TODO: handle exception
			helper.returnResource(jedis);
		}
	}

}
