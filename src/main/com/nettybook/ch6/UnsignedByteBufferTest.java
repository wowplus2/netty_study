package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class UnsignedByteBufferTest 
{
	final String src = "Hello world";
	
	@Test
	public void unsignedBufferToJavaBuffer() {
		ByteBuf buf = Unpooled.buffer(11);
		
		/* 빈 바이트 버퍼에 음수 1을 기록한다.
		 * -1은 부호있는 16진수 표기법에서 0xFFFF와 같이 표현되고 -2는 0xFFFE로 표현된다.
		 * 0xFFFF를 부호 없는 정수로 표현하면 65535가 된다. */
		buf.writeShort(-1);
		/* getUnsignedShort 메서드로 바이트 버퍼에 
		 * 저장된 데이터의 0번쨰 바이트부터 2바이트를 읽어서 4바이트 데이터인 int로 읽어들이면 65535가 된다. */
		assertEquals(65535, buf.getUnsignedShort(0));
	}
}
