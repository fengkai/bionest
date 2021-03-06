package com.zkteco.bionest.autoconfigure.web;

public class CustomErrorBody {

	private final Integer status;

	private final String message;

	public CustomErrorBody(Integer status, String message) {
		this.status = status;
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
}
