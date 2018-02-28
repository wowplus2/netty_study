package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;

import org.junit.Test;

public class ByteBufferTest3RightWay 
{
	@Test
	public void test() {
		ByteBuffer trdBuffer = ByteBuffer.allocate(11);
		System.out.println("초기 상태:\t" + trdBuffer);
		
		trdBuffer.put((byte) 1);
		trdBuffer.put((byte) 2);
		assertEquals(2, trdBuffer.position());
		
		/* position 속성을 0으로 변경한다. */
		trdBuffer.rewind();
		assertEquals(0, trdBuffer.position());
		
		/* 바이트 버퍼에 저장된 첫번째 값을 조회한다. */
		assertEquals(1, trdBuffer.get());
		/* 바이트 버퍼에 position 속성값이 1 증가한다. */
		assertEquals(1, trdBuffer.position());
		 
		System.out.println("현재 상태:\t" + trdBuffer);
	}

}
