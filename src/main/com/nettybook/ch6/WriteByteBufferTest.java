package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class WriteByteBufferTest 
{
	@Test
	public void writeTest() {
		/* byte형 11개를 지정할 수 있는 다이렉트 버퍼를 생성한다. */
		ByteBuffer fstByteBuff = ByteBuffer.allocateDirect(11);
		/* 생성된 fstByteBuff의 position 속성이 0인지 검사한다. */
		assertEquals(0, fstByteBuff.position());
		/* 생성된 fstByteBuff의 limit 속성이 11인지 검사한다. */
		assertEquals(11, fstByteBuff.limit());
		
		/* 바이트 버퍼에 1부터 4까지 4개의 데이터를 기록한다. */
		fstByteBuff.put((byte) 1);
		fstByteBuff.put((byte) 2);
		fstByteBuff.put((byte) 3);
		fstByteBuff.put((byte) 4);
		/* 데이터를 기록한 이후의 position 속성값이 4인지 확인한다.
		 * 즉 한바이트를 저장하는 put 메서드가 4번호출되었기 때문에 position 속성값이 4이다.
		 * 여기에서 유의해야 할 부분은 position 속성값이 저장된 바이트 길이가 아니라 저장된 요소 개수라는 점이다. */
		assertEquals(4, fstByteBuff.position());
		/* limit 속성이 초기 생성값과 동일하게 11인지 확인한다. */
		assertEquals(11, fstByteBuff.limit());
		
		/* flip 메서드를 호출한다. */
		fstByteBuff.flip();
		/* flip 메서드 호출 이후의 position 속성값이 0으로 변경되었는지 확인한다. */
		assertEquals(0, fstByteBuff.position());
		/* flip 메서드 호출 이후의 limit 속성값이 4로 변경되었는지 확인한다. */
		assertEquals(4, fstByteBuff.limit());
	}
}
