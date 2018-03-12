package com.nettybook.ch9.service.dao;

import com.nettybook.ch9.core.KeyMaker;

import redis.clients.util.MurmurHash;

public class TokenKey implements KeyMaker 
{
	static final int SEED_MURMURHASH = 0x1234ABCD;
	
	private String email;
	private long issueDate;
	
	/* 키 메이커 클래스를 위한 생성자. */
	public TokenKey(String email, long issueDate) {
		// TODO Auto-generated constructor stub
		this.email = email;
		this.issueDate = issueDate;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		String src = email + String.valueOf(issueDate);
		
		return Long.toString(MurmurHash.hash64A(src.getBytes(), SEED_MURMURHASH), 16);
	}

}
