package com.nettybook.ch6;

import java.nio.ByteBuffer;

public class ByteBufferTest3 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ByteBuffer trdBuffer = ByteBuffer.allocate(11);
		System.out.println("초기 상태:\t" + trdBuffer);
		
		/* put 메서드가 실행 될때 저장된 데이터의 길이만큼 position 속성값이 1증가한다. */
		trdBuffer.put((byte) 1);
		/* get 메서드 실행 시에도 읽어들인 길이만큼 position 속성값이 증가한다. */
		System.out.println(trdBuffer.get());
		System.out.println(trdBuffer);
	}

}
