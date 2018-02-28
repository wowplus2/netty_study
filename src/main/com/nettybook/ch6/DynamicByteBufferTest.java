package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class DynamicByteBufferTest 
{
	@Test
	public void createUnpooledHeapBufferTest() {
		ByteBuf buf = Unpooled.buffer(11);
		testBuffer(buf, false);
	}
	
	@Test
	public void createUnpooledDirectBufferTest() {
		ByteBuf buf = Unpooled.directBuffer(11);
		testBuffer(buf, true);
	}
	
	@Test
	public void createPooledHeapBufferTest() {
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
		testBuffer(buf, false);
	}
	
	@Test
	public void createPoolesDirectBufferTest() {
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
		testBuffer(buf, true);
	}
	
	
	private void testBuffer(ByteBuf buf, boolean isDirect) {
		assertEquals(11, buf.capacity());
		assertEquals(isDirect, buf.isDirect());
		
		String src = "hello world";
		
		/* 11바이트를 저장할 수 있는 바이트 버퍼에 "hello world" 문자열을 저장한다. */
		buf.writeBytes(src.getBytes());
		/* 바이트 버퍼에서 읽어들일 수 있는 바이트 크기가 11인지 확인한다.
		 * 바이트 버퍼의 readableBytes 메서드는 위에서 기록한 "hello world" 문자열을 돌려준다. */
		assertEquals(11, buf.readableBytes());
		/* 바이트 버퍼에 기록할 수 있는 공간이 0인지 확인한다. */
		assertEquals(0, buf.writableBytes());
		
		/* 바이트 버퍼에 저장된 데이터를 문자열로 반환하고 그 결과가 "hello world" 문자열과 같은지 확인한다. */
		assertEquals(src, buf.toString(Charset.defaultCharset()));
		
		/* 바이트 버퍼의 크기를 6바이트로 줄인다. 
		 * 네티의 바이트 버퍼는 capacity 메서드를 사용하여 바이트 버퍼의 크기를 동적으로 조절할 수 있다.
		 * 단 저장된 데이터보다 작은 크기로 조절하면 나머지 데이터는 잘려진다. */
		buf.capacity(6);
		/* 위에서 호출한 capacity 메서드로 인하여 바이트 버퍼에 저장된 "world" 문자열이 잘려졌다. */
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		assertEquals(6, buf.capacity());
		
		/* 바이트 버퍼의 크기를 13으로 늘린다.
		 * 바이트 버퍼의 크기를 늘리면 기존에 저장된 데이터는 보존된다. */
		buf.capacity(13);
		/* 늘어난 capacity(13) 메서드에 의해 늘어난 바이트 버퍼에 저장된 데이터가 보존되어 있는지 확인한다. */
		assertEquals("hello ", buf.toString(Charset.defaultCharset()));
		
		/* 주어진 바이트 버퍼에 "world" 문자열을 추가한다.
		 * 기존에 보존된 문자열에 "world"를 추가했으므로 바이트 버퍼에 저장된 데이터는 맨 처음 저장한 "hello world"와 같이 된다. */
		buf.writeBytes("world".getBytes());
		/* 문자열 추가작업으로 갱신된 데이터가 초기 데이터인 "hello world"와 같은지 확인한다. */
		assertEquals(src, buf.toString(Charset.defaultCharset()));
		
		assertEquals(13, buf.capacity());
		/* 주어진 바이트 버퍼에 남은 바이트 수가 2인지 확인한다.
		 * 13바이트 크기의 바이트 버퍼에 11바이트를 기록하였으므로 기록 가능한 남은 바이트 수는 2이다. */
		assertEquals(2, buf.writableBytes());
	}
}
