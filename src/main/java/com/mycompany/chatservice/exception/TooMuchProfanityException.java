package com.mycompany.chatservice.exception;

public class TooMuchProfanityException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public TooMuchProfanityException(String message) {
		super(message);
	}
}
