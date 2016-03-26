package com.zjut.oj.util;

public class ResponseMessage {
	public static final int SUCCESS = 1;
	public static final int FAILE = 0;
	public static final int NOT_LOGIN = 2;
	
	private int code;
	private String msg;
	private Object obj;
	
	public ResponseMessage() {
		this.code = SUCCESS;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
