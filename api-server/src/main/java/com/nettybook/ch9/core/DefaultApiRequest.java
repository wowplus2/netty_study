package com.nettybook.ch9.core;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.nettybook.ch9.service.ServiceException;

@Service("notFound")
@Scope("prototype")
public class DefaultApiRequest extends ApiRequestTemplate 
{
	public DefaultApiRequest(Map<String, String> reqData) {
		super(reqData);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void service() throws ServiceException {
		// TODO Auto-generated method stub
		this.apiResult.addProperty("rec_cd", "404");
	}	
}
