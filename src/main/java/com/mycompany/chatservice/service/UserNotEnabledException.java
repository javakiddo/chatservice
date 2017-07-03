package com.mycompany.chatservice.service;

import org.springframework.security.core.AuthenticationException;

public class UserNotEnabledException extends AuthenticationException
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 public UserNotEnabledException(String msg, Throwable t) {
	        super(msg, t);
	    }

	    public UserNotEnabledException(String msg) {
	        super(msg);
	    }
}
