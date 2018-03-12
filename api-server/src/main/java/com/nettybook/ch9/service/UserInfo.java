package com.nettybook.ch9.service;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nettybook.ch9.core.ApiRequestTemplate;


/* 스프링의 Service 어노테이션은 스프링 컨텍스트가 UserInfo 클래스를 생성할 수 있도록 하며 문자열로 지정된
 * users는 스프링 컨텍스트에서 클래스의 객체를 생성할 때 사용할 이름이다.
 * 즉 getBean 메서드 호출의 인자로 사용된다. */
@Service("users")
/* 스프링의 Scope 어노테이션은 스프링 컨텍스트가 객체를 생성할 때 싱글톤으로 생성할 것인지 아니면
 * 객체를 요청할 때마다 새로 생성할 것인지를 설정한다.
 * 여기에 설정된 prototype 값은 요청할 때마다 새로 생성한다는 의미이며 이 어노테이션을 지정하지 않으면 싱글톤으로 생성된다. */
@Scope("prototype")
public class UserInfo extends ApiRequestTemplate 
{
	@Autowired
	/* 앞에서 설정한 HSQLDB와 마이바티스 스프링 설정을 기초로 하여 마이바티스의 sqlSession 객체를 생성하여 할당한다. */
	private SqlSession sqlSession;
	
	/* HTTP 요청의 파라메터값을 인수로하여 사용자 정보 조회 API 클래스가 생성된다. */
	public UserInfo(Map<String, String> reqData) {
		super(reqData);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	/* 정의한 파라메터가 정상적으로 입력되었는지 확인한다.
	 * requestParamValidation 메서드는 ApiRequestTemplate 클래스의 executeService 메서드에서 자동 호출된다. */
	public void requestParamValidation() throws RequestParamException {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(this.reqData.get("email"))) {
			throw new RequestParamException("email정보가 전달되지 않았습니다.");
		}
	}


	@Override
	public void service() throws ServiceException {
		// TODO Auto-generated method stub
		/* 입력 email 사용자의 이메일을 HTTP Header에 입력한다.
		 * 출력 res_cd API 처리 결과코드를 돌려준다. API 처리 결과가 정상이면 결과코드는 200이다.
		 * 출력 message API 처리 결과 메세지를 돌려준다. API의 처리 결과가 정상일 때는 success 메세지를 돌려주며
		 * 나머지 정상이 아닐 때는 오류 메세지를 돌려준다. 
		 * 출력 usridx에 입력된 이메일에 해당하는 사용자는 사용자 번호를 돌려준다. */
		
		/* users.xml에 정의된 userInfoByEmail 쿼리를 수행한다.
		 * 쿼리에 입력되는 파라메터는 HTTP 요청에서 입력된 필드와 매칭된다. */
		Map<String, Object> result = sqlSession.selectOne("users.userInfoByEmail", this.reqData);
		
		if (result != null) {
			String uIdx = String.valueOf(result.get("UIDX"));
			
			// helper.
			/* 쿼리의 실행이 정상적이므로 응답코드에 200을 설정한다. */
			this.apiResult.addProperty("res_cd", "200");
			this.apiResult.addProperty("message", "success");
			this.apiResult.addProperty("uidx", uIdx);
		} else {
			// 데이터 없음.
			/* 쿼리의 결과가 존재하지 않으면 입력된 이메일이 존재하지 않는다는 의미이므로 응답코드로 404를 돌려준다. */
			this.apiResult.addProperty("res_cd", "404");
			this.apiResult.addProperty("message", "fail:not_found");
		}
	}
	
	

}
