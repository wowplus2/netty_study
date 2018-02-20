package com.nettybook.ch2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class BlockingServer 
{
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		BlockingServer server = new BlockingServer();
		server.run();
	}
	
	private void run() throws IOException {
		ServerSocket server = new ServerSocket(8888);
		System.out.println("접속 대기 중...");
		
		while (true) {
			Socket sock = server.accept();
			System.out.println("클라이언트 연결됨.");
			
			OutputStream out = sock.getOutputStream();
			InputStream in = sock.getInputStream();
			
			while (true) {
				try {
					int req = in.read();
					out.write(req);
				} catch (Exception e) {
					// TODO: handle exception
					break;
				}
			}
		}
	}

}
