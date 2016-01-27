package com.hp.wx.exception;

public class WXControllerException extends Exception {

	public WXControllerException() {
		super();
	}

	public WXControllerException(String msg) {

		super(msg);

	}

	public WXControllerException(String msg, Throwable cause) {

		super(msg, cause);

	}

	public WXControllerException(Throwable cause) {

		super(cause);

	}
}
