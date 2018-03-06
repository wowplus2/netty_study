package com.nettybook.ch8.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ResponseGeneratorTest 
{
	@Test
	public void testZeroLengthString() {
		String req = "";
		
		ResponseGenerator rg = new ResponseGenerator(req);
		/* 빈 문자열을 입력하여 생성된 ResponseGenerator 클래스의 객체가 정상인지 테스트한다.
		 * 만약 테스트가 실패했다면 객체 생성에 실패한 것이다. */
		assertNotNull(rg);
		
		/* 빈 문자열이 입력되었을 때 response 메서드의 결과로 응답 문자열이 생성되는지 테스트한다.
		 * 만약 응답 문자열이 null이라면 테스트에 실패한 것이다. */
		assertNotNull(rg.response());
		/* 빈 문자열이 입력되었을 때 '명령을 입력해 주세요.\r\n'와 동일한지 테스트 한다.
		 * 만약 빈 문자열이 입력된 ResponseGenerator 객체의 response 메서드 호출 결과로 다른 문자열을 
		 * 되돌려 받는다면 테스트에 실패한 것이다. */
		assertEquals("명령을 입력해 주세요.\r\n", rg.response());
		
		/* 빈 문자열이 입력되었을 때 isClose 메서드의 응답이 false 인지 테스트 한다. */
		assertFalse(rg.isClose());
	}
	
	@Test
	public void testHi() {
		String req = "hi";
		
		ResponseGenerator rg = new ResponseGenerator(req);
		assertNotNull(rg);
		
		assertNotNull(rg.response());
		assertEquals("입력하신 명령이 '" + req + "' 입니까?\r\n", rg.response());
		
		assertFalse(rg.isClose());
	}
	
	@Test
	public void testBye() {
		String req = "bye";
		
		ResponseGenerator rg = new ResponseGenerator(req);
		assertNotNull(rg);
		
		assertNotNull(rg.response());
		assertEquals("Have a good day!\r\n", rg.response());
		
		assertTrue(rg.isClose());
	}
}
