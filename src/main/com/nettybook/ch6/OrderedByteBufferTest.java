package com.nettybook.ch6;

import static org.junit.Assert.assertEquals;

import java.nio.ByteOrder;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class OrderedByteBufferTest 
{
	@Test
	public void pooledHeapBufferTest() {
		ByteBuf buf = Unpooled.buffer(11);
		/* 옵션 없이 생성한 네티 바이트 버퍼의 엔디안이 빅엔디안인지 확인한다. */
		assertEquals(ByteOrder.BIG_ENDIAN, buf.order());
		
		/* 바이트 버퍼에 숫자 1을 2바이트 Short형으로 기록한다.
		 * 바이트 버퍼의 기본 엔디안은 빅엔디안이므로 0x0001이 저장된다. */
		buf.writeShort(1);
		/* markReaderIndex 메서드로 현재 바이트 버퍼의 읽기 인덱스 위치를 표시한다.
		 * markReaderIndex 메서드로 표시한 읽기 인덱스 위치로 돌아가려면 resetReaderIndex 메서드를 사용한다. */
		buf.markReaderIndex();
		/* 저장한 데이터가 1인지 확인한다. 빅엔디안으로 저장된 데이터를 그대로 읽는다. */
		assertEquals(1, buf.readShort());
		
		/* 읽기 인덱스의 위치를 markReaderIndex를 사용하여 표시한 위치로 이동시킨다. */
		buf.resetReaderIndex();
		
		/* 바이트 버퍼의 order 메서드로 리틀엔디안의 바이트 버퍼를 생성한다.
		 * 여기서 생성된 바이트 버퍼는 바이트 버퍼 내부의 배열과 읽기 인덱스, 쓰기 인덱스를 공유한다.
		 * 즉 내용은 동일하지만 리틀엔디안에 해당하는 읽기 쓰기 메서드를 제공하는 바이트 버퍼 객체를 얻을 수 있다. */
		ByteBuf lettleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
		/* 리틀엔디안에 해당하는 2바이트 Short형 데이터를 읽고 그 값이 256인지 확인한다.
		 * 빅엔디안인 0x001을 리틀엔디안으로 변환하면 0x0100이 되므로 십진수 256이 된다. */
		assertEquals(256, lettleEndianBuf.readShort());
	}
}
