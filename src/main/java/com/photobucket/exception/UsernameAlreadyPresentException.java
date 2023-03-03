package com.photobucket.exception;

public class UsernameAlreadyPresentException extends RuntimeException{
	public UsernameAlreadyPresentException(String msg) {
		super(msg);
	}
}
