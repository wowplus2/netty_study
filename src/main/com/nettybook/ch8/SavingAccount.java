package com.nettybook.ch8;

public class SavingAccount 
{
	private int balance = 1000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public int withdraw(int amt) {
		balance = balance - amt;
		return balance;
	}
	
	public int deposit(int amt) {
		balance = balance + amt;
		return balance;
	}
}
