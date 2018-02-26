package com.nettybook.ch4;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;


/* Handler implementation for the echo client. It initiates the ping-pong traffic between the echo client and server by 
 * sending the first message to the server. */
public class EchoClientHandler1 extends ChannelOutboundHandlerAdapter 
{
	@Override
	public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("connect 메서드 호출: " + remoteAddress + " / " + localAddress);
		super.connect(ctx, remoteAddress, localAddress, promise);
	}

	@Override
	public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("close 메서드 호출...");
		super.close(ctx, promise);
	}

	@Override
	public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("deregister 메서드 호출...");
		super.deregister(ctx, promise);
	}

	@Override
	public void read(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("read 메서드 호출...");
		super.read(ctx);
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("write 메서드 호출...");
		super.write(ctx, msg, promise);
	}

	@Override
	public void flush(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("flush 메서드 호출...");
		super.flush(ctx);
	}

	@Override
	public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("disconnect 메서드 호출...");
		super.disconnect(ctx, promise);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
}
