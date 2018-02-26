package com.nettybook.ch5;

import io.netty.util.concurrent.Future;


public class SpecialFutureCake 
{
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Bakery bakery = new Bakery();
		
		// 케이크를 준문하고 주문서를 받는다.
		Future future = bakery.orderCake();
		
		// 다른 일을 한다...
		doSomething();
		// ...
		
		// 케이크가 완성되었는지 확인한다.
		if (future.isDone()) {
			//Cake cake = future.getCake();
		}
		else {
			// 케이크가 완성 되었는지 확인한다.
			while (future.isDone() != true) {
				// 다른 일을 한다...
				doSomething();
				// ...
			}
			
			// while 루프를 빠져나왔으므로 완성된 케이크를 수령한다.
			// Cake cake = future.getCake();
		}
	}
	
	private static void doSomething() throws InterruptedException {
		Thread.sleep(100);
	}
}

class Bakery {
	public Future orderCake() {
		return null;
	}
}

class Cake {
	
}
