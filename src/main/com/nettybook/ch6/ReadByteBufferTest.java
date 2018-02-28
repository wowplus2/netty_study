package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ReadByteBufferTest 
{
	@Test
	public void readTest() {
		
		byte[] tmpArr = { 1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0 };
		/* byte형 11개가 저장된 tmpArr 바이트 배열을 사용하여 바이트 버퍼 객체를 생성한다. */
		ByteBuffer rByteBuff = ByteBuffer.wrap(tmpArr);
		/* 생성된 rByteBuff의 position 속성이 0인지 검사한다. */
		assertEquals(0, rByteBuff.position());
		/* 생성된 rByteBuff의 limit 속성이 11인지 검사한다. */
		assertEquals(11, rByteBuff.limit());
		
		/* 바이트 버퍼에서 1부터 4까지 4개의 데이터를 조회한다. */
		assertEquals(1, rByteBuff.get());
		assertEquals(2, rByteBuff.get());
		assertEquals(3, rByteBuff.get());
		assertEquals(4, rByteBuff.get());
		
		/* 데이터 조회한 이후의 position 속성값이 4인지 확인한다. 
		 * 즉 한바이트를 조회하는 get 메서드가 4번 호출되었기 때문에 position 속성값은 4이다. */
		assertEquals(4, rByteBuff.position());
		/* limit 속성이 초기값과 동일하게 11인지 확인한다. */
		assertEquals(11, rByteBuff.limit());
		
		/* flip 메서드를 호출한다. */
		rByteBuff.flip();
		/* flip 메서드 호출 이후의 position 속성값이 0으로 변경되었는지 확인한다. */
		assertEquals(0, rByteBuff.position());
		/* flip 메서드 호출 이후의 limit 속성값이 4로 변경되었는지 확인한다. */
		assertEquals(4, rByteBuff.limit());
		
		/* 바이트 버퍼의 3번째 요소의 값을 조회한다. */
		rByteBuff.get(3);
		/* 바이트 버퍼의 get 메서드를 호출한 이후의 position 메서드가 0인지 확인한다. */
		assertEquals(0, rByteBuff.position());
		/* 바이트 버퍼의 get 메서드를 호출한 이후의 limit 메서드가 4인지 확인한다. */
		assertEquals(4, rByteBuff.limit());
	}
}
