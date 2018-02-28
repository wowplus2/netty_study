package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ConvertByteBufferTest 
{
	final String src = "Hello world";
	
	@Test
	public void convertNettyBufferToJavaBuffer() {
		/* 11바이트를 저장할 수 있는 네티 바이트 버퍼를 생성한다. */
		ByteBuf buf = Unpooled.buffer(11);
		
		/* 생성된 네티 바이트 버퍼에 "Hello world" 문자열을 저장한다. */
		buf.writeBytes(src.getBytes());
		/* 네티 바이트 버퍼에 저장된 내용을 문자열로 변환하여 초기 문자열과 같은지 확인한다. */
		assertEquals(src, buf.toString(Charset.defaultCharset()));
		
		/* 네티 바이트 버퍼의 nioBuffer 메서드로 자바 바이트 버퍼 객체를 생성한다.
		 * 여기서 생성한 자바 바이트 버퍼와 네티 바이트 버퍼의 내부 배열은 서로 공유된다. */
		ByteBuffer nioByteBuffer = buf.nioBuffer();
		/* 자바 바이트 버퍼(nioByteBuffer)가 null이 아닌지 확인한다. */
		assertNotNull(nioByteBuffer);
		/* 자바 바이트 버퍼의 내용을 문자열로 변환하고 변환된 문자열이 "Hello world"와 같은지 확인한다. */
		assertEquals(src, new String(nioByteBuffer.array(), nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
		//assertEquals(src, new String(nioByteBuffer.array()));
	}
	
	@Test
	public void convertJavaBufferToNettyBuffer() {
		/* "Hello world" 문자열을 사용하여 자바 바이트 버퍼 객체를 생성한다. */
		ByteBuffer byteBuffer = ByteBuffer.wrap(src.getBytes());
		/* 네티 Unpooled 클래스의 wrappedBuffer 메서드에 자바 바이트 버퍼를 입력하여 네티 바이트 버퍼를 생성한다.
		 * 여기서 생성한 자바 바이트 버퍼와 네티 바이트 버퍼의 내부 배열을 서로 공유된다.
		 * 이 같이 내부 배열을 공유하는 바이트 버퍼를 뷰 버퍼라고 한다. */
		ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);
		
		/* 네티 바이트 버퍼에 저장된 내용을 문자열로 변환하여 "Hello world" 문자열과 같은지 확인한다. */
		assertEquals(src, nettyBuffer.toString(Charset.defaultCharset()));
	}
}
