package com.zkteco.bionest.boot.web;

import lombok.Data;

import org.springframework.lang.Nullable;

@Data
public class Result<T> {

	private Integer code;

	private String state;

	@Nullable
	private String message;

	private T data;

	public static <T> Result success(T data) {
		Result<T> result = new Result<>();
		result.setCode(0);
		result.setState("success");
		result.setData(data);
		return result;
	}

	public static <T> Result fail(Integer code, String message) {
		Result<T> result = new Result<>();
		result.setCode(code);
		result.setState("fail");
		result.setMessage(message);
		return result;
	}
}
