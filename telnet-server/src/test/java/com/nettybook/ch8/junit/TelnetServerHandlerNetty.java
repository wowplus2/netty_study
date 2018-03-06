package com.nettybook.ch8.junit;

import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetAddress;
import java.util.Date;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;

/* Handlers a server-side channel. */
@Sharable
public class TelnetServerHandlerNetty extends SimpleChannelInboundHandler<String> 
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		// Send greeting for a new connection.
		ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
		ctx.write("It is " + new Date() + " now.\r\n");
		ctx.flush();
		System.out.println(InetAddress.getLocalHost().getHostName() + " channelActive ");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String req) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(req + " channelActive ");
		// Generate and write a response.
		String resp;
		boolean close = false;
		
		if (req.isEmpty()) {
			resp = "Please type something...\r\n";
		} else if ("bye".equals(req.toLowerCase())) {
			resp = "Have a goof day!\r\n";
			close = true;
		} else {
			resp = "Did you say '" + req + "'?\r\n";
		}
		
		// We do not need to write a ChannelBuffer here.
		// We know the encoder inserted at TelnetPipelineFactory will do the conversion.
		ChannelFuture f = ctx.write(resp);
		
		// Close the connection after sending 'Have a good day!'
		// if the client has sent 'bye'
		System.out.println(resp);
		if (close) {
			f.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		cause.printStackTrace();
		ctx.close();
	}
	
		
}
