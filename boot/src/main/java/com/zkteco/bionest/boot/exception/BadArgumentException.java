package com.zkteco.bionest.boot.exception;

/**
 * Bad arg exception.
 */
public class BadArgumentException extends RuntimeException{

	private final String field;

	private final String message;

	public BadArgumentException(String field, String message) {
		this.field = field;
		this.message = message;
	}

}
