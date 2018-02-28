package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class CreateByteBufferTestByNetty 
{
	@Test
	public void createUnpooledHeapBufferTest() {
		/* 바이트 버퍼 풀을 사용하지 않는 11바이트 크기의 힙 버퍼를 생성한다. */
		ByteBuf buf = Unpooled.buffer(11);
		/* 생성한 바이트 버퍼가 정상인지 확인한다. */
		testBuffer(buf, false);
	}
	
	@Test
	public void createUnpooledDirectBufferTest() {
		/* 바이트 버퍼 풀을 사용하지 않는 11바이트 크기의 다이렉트 버퍼를 생성한다. */
		ByteBuf buf = Unpooled.directBuffer(11);
		testBuffer(buf, true);
	}
	
	@Test
	public void createPooledHeapBufferTest() {
		/* 풀링된 11바이트 크기의 힙버퍼를 생성한다. */
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
		testBuffer(buf, false);
	}
	
	@Test
	public void createPooledDirectBufferTest() {
		/* 풀링된 11바이트 크기의 다이렉트버퍼를 생성한다. */
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
		testBuffer(buf, true);
	}
	
	
	private void testBuffer(ByteBuf buf, boolean isDirect) {
		/* 입력된 바이트 버퍼의 크기가 11인지 확인한다. */
		assertEquals(11, buf.capacity());
		/* 주어진 바이트 버퍼가 다이렉트 버퍼인지 확인한다. */
		assertEquals(isDirect, buf.isDirect());
		
		/* 주어진 바이트 버퍼에서 읽을 수 있는 바이트 수가 0인지 확인한다.
		 * 네티 바이트 버퍼의 초기 속성값은 읽기index와 쓰기index 모두 0이다. 
		 * 그러므로 읽을 수 있는 바이트 수는 0이 되며 쓸 수 있는 바이트 수는 바이트 버퍼의 크기인 11이 된다. */
		assertEquals(0, buf.readableBytes());
		assertEquals(11, buf.writableBytes());
	}
}
