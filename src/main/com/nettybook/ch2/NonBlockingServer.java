package com.nettybook.ch2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NonBlockingServer 
{
	private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
	private ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);

	private void startEchoServer() {
		/* JDK1.7에서 새로 등장한 기능으로서 try 블록이 끝날 때 소괄호 안에서 선언된 자원을 자동으로 해제해준다.
		 * 물론 try 블록이 비정상적으로 끝나서 catch 블록으로 이동하더라도 자원은 정상적으로 해제된다.
		 * JDK1.6 이전버전 에서는 반드시 finally 구문을 작성하여 자원을 해제해야 했다. */
		try (
				/* 자바NIO 컴포넌트 중의 하나인 Selector는 자신에게 등록된 채널에 변경 사항이 발생했는지 검사하고 
				 * 변경 사항이 발생한 채널에 대한 접근을 가능하게 해준다.
				 * 여기서는 새로운 Selector 객체를 생성했다. */
				Selector selector = Selector.open(); 
				/* 블로킹 소켓의 ServerSocket에 대응되는 논블로킹 소켓의 서버 소켓 채널을 생성한다.
				 * 블로킹 소켓과 다르게 소켓 채널을 먼저 생성하고 사용할 포트를 바인딩한다. */
				ServerSocketChannel srvSocketChannel = ServerSocketChannel.open();
			) {
			
			/* 생성한 Selector와 ServerSocketChannel 객체가 정상적으로 생성되었는지 확인한다. */
			if (srvSocketChannel.isOpen() && selector.isOpen()) {
				/* 소켓 채널의 블로킹모드의 기본값은 true이다.
				 * 즉, 별도로 논블로킹 모드로 지정하지 않으면 블로킹 모드로 동작한다.
				 * 여기서는 ServerSocketChannel 객체를 논블로킹 모드로 설정했다. */
				srvSocketChannel.configureBlocking(false);
				/* 클라이언트의 연결을 대기할 포트를 지정하고 생성된 ServerSocketChannel 객체에 할당한다.
				 * 이 작업이 완료되면 ServerSocketChannel 객체가 지정된 포트로부터 클라이언트의 연결을 생성할 수 있다. */
				srvSocketChannel.bind(new InetSocketAddress(8888));
				/* ServerSocketChannel 객체를 Selector 객체에 등록한다.
				 * Selector가 감지할 이벤트는 연결 요청에 해당하는 SelectionKey.OP_ACCEPT이다. */
				srvSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
				System.out.println("접속 대기 중...");

				while (true) {
					/* Selector에 등록된 이벤트에서 변경사항이 발생했는지 검사한다.
					 * Selector에 아무런 I/O 이벤트도 발생하지 않으면 스레드는 이부분에서 블로킹된다. 
					 * I/O이벤트가 발생하지 않았을 때 블로킹을 피하고 싶다면 selectNow 메서드를 사용하면 된다. */
					selector.select();
					/* Selector에 등록된 채널 중에서 I/O이벤트가 발생한 채널들의 목록을 조회한다. */
					Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

					while (keys.hasNext()) {
						SelectionKey key = (SelectionKey) keys.next();
						/* I/O이벤트가 발생한 채널에서 동일한 이벤트가 감지되는 것을 방지하기 위하여 조회된 목록에서 해당 중복 이벤트를 제거한다. */
						keys.remove();

						if (!key.isValid()) {
							continue;
						}
						
						/* 조회된 I/O 이벤트의 종류가 연결 요청인지 획인한다.
						 * 만약 연결 요청 이벤트라면 연결처리 메서드로 이동한다. */
						if (key.isAcceptable()) {
							this.acceptOP(key, selector);
						}
						/* 조회된 I/O 이벤트의 종류가 데이터 수신인지 확인한다.
						 * 만약 데이터 수신 이벤트라면 데이터 읽기처리 메서드로 이동한다. */
						else if (key.isReadable()) {
							this.readOP(key);
						}
						/* 조회된 I/O 이벤트의 종류가 데이터 쓰기가능인지 확인한다. 
						 * 만약 데이터 쓰기가능 이벤트라면 데이터 쓰기처리 메서드로 이동한다. */
						else if (key.isWritable()) {
							this.writeOP(key);
						}
					}
				}
			} else {
				System.out.println("서버 소켓을 생성하지 못했습니다...");
			}

		} catch (IOException ex) {
			// TODO: handle exception
			System.err.println(ex);
		}
	}

	private void writeOP(SelectionKey key) throws IOException {
		// TODO Auto-generated method stub
		SocketChannel sockChannel = (SocketChannel) key.channel();
		
		List<byte[]> chData = keepDataTrack.get(sockChannel);
		Iterator<byte[]> its = chData.iterator();
		
		while (its.hasNext()) {
			byte[] it = its.next();
			its.remove();
			sockChannel.write(ByteBuffer.wrap(it));
		}
		
		key.interestOps(SelectionKey.OP_READ);
	}

	private void readOP(SelectionKey key) {
		// TODO Auto-generated method stub
		try {
			SocketChannel sockChannel = (SocketChannel) key.channel();
			buffer.clear();

			int numRead = -1;

			try {
				numRead = sockChannel.read(buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("데이터 읽기 에러발생...");
				e.printStackTrace();
			}

			if (numRead == -1) {
				this.keepDataTrack.remove(sockChannel);
				System.out.println("클라이언트 연결종료: " + sockChannel.getRemoteAddress());

				sockChannel.close();
				key.cancel();
				return;
			}
			
			byte[] data = new byte[numRead];
			System.arraycopy(buffer.array(), 0, data, 0, numRead);
			System.out.println(new String(data, "UTF-8") + " from " + sockChannel.getRemoteAddress());
			
			doEchoJob(key, data);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e);
		}

	}

	private void acceptOP(SelectionKey key, Selector selector) throws IOException {
		// TODO Auto-generated method stub
		/* 연결요청 이벤트가 발생한 채널은 항상 ServerSocketChannel 이므로 이벤트가 발생한 채널을 ServerSocketChannel로 캐스팅한다. */
		ServerSocketChannel srvSockChannel = (ServerSocketChannel) key.channel();
		/* ServerSocketChannel을 사용하여 클라이언트의 연결을 수락하고 연결된 소켓채널을 가져온다. */
		SocketChannel sockChannel = srvSockChannel.accept();
		/* 연결된 클라이언트 소켓채널을 논블로킹 모드로 설정한다. */
		sockChannel.configureBlocking(false);

		System.out.println("클라이언트 연결됨: " + sockChannel.getRemoteAddress());

		keepDataTrack.put(sockChannel, new ArrayList<byte[]>());
		/* 클라이언트 소켓채널을 Selector에 등록하여 I/O 이벤트를 감시한다. */
		sockChannel.register(selector, SelectionKey.OP_READ);
	}
	
	private void doEchoJob(SelectionKey key, byte[] data) {
		// TODO Auto-generated method stub
		SocketChannel sockChannel = (SocketChannel) key.channel();
		List<byte[]> chData = keepDataTrack.get(sockChannel);
		
		chData.add(data);
		key.interestOps(SelectionKey.OP_WRITE);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NonBlockingServer main = new NonBlockingServer();
		main.startEchoServer();
	}

}
