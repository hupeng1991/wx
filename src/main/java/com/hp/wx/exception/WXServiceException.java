package com.hp.wx.exception;

public class WXServiceException extends Exception {

	public WXServiceException() {
		super();
	}

	public WXServiceException(String msg) {

		super(msg);

	}

	public WXServiceException(String msg, Throwable cause) {

		super(msg, cause);

	}

	public WXServiceException(Throwable cause) {

		super(cause);

	}

}
