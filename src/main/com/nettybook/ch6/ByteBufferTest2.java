package com.nettybook.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest2 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer sndBuffer = ByteBuffer.allocate(11);
		/* 11바이트를 저장할 수 있는 초기화된 바이트 버퍼의 position, limit, capacity 속성값을 출력한다. */
		System.out.println("초기화 상태:\t" + sndBuffer);
		
		byte[] src = "Hello world!".getBytes();
		
		/* 바이트 버퍼에 입력할 데이터인 "Hello world!" 데이터의 바이트 길이만큼 반복 수행한다. */
		for (byte item : src) {
			/* ByteBuffer 클래스의 put(byte b) 메서드는 바이트 버퍼에 한 바이트를 기록하고 position위 위치를 1 증가시킨다. */
			sndBuffer.put(item);
			System.out.println("현재 상태:\t" + sndBuffer);
		}
	}

}
