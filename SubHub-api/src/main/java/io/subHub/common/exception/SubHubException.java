package io.subHub.common.exception;


import io.subHub.common.utils.MessageUtils;

/**
 * Custom exceptions
 *
 * @author By
 */
public class SubHubException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int code;
	private String msg;

	public SubHubException(int code) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public SubHubException(int code, String... params) {
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public SubHubException(int code, Throwable e) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code);
	}

	public SubHubException(int code, Throwable e, String... params) {
		super(e);
		this.code = code;
		this.msg = MessageUtils.getMessage(code, params);
	}

	public SubHubException(String msg) {
		super(msg);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public SubHubException(String msg, Throwable e) {
		super(msg, e);
		this.code = ErrorCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
