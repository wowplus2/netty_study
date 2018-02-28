package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

import org.junit.Test;

public class CreateByteBufferTest 
{
	@Test
	public void createTest() {
		/* 11개의 char형 데이터를 저장할 수 있는 힙버퍼를 생성한다. */
		CharBuffer heapBuffer = CharBuffer.allocate(11);
		/* heapBuffer의 capacity 속성이 11인지 검사한다. */
		assertEquals(11, heapBuffer.capacity());
		/* heapBuffer가 다리렉트 버퍼인지 검사한다. 
		 * isDirect 메서드는 주어진 버퍼가 allocateDirect 메서드를 사용하여 생성되는지 검사한다. */
		assertEquals(false, heapBuffer.isDirect());
		
		/* 11개의 byte형 버퍼를 저장할 수 있는 다이렉트 버퍼를 생성한다. */
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(11);
		assertEquals(11, directBuffer.capacity());
		assertEquals(true, directBuffer.isDirect());
		
		/* 11개의 데이터가 저장된 int형 배열을 생성한다. */
		int[] array = {1,2,3,4,5,6,7,8,9,0,0};
		/* 생성한 int형 array 배열을 감싸는 int형 버퍼를 생성한다.
		 * 이때 생성되는 버퍼는 JVM의 heap 영역에 생성된다. */
		IntBuffer intHeapBuffer = IntBuffer.wrap(array);
		assertEquals(11, intHeapBuffer.capacity());
		assertEquals(false, intHeapBuffer.isDirect());
	}
}
 