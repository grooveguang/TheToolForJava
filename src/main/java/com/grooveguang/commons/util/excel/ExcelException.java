package com.grooveguang.commons.util.excel;

import com.grooveguang.commons.exception.BaseException;

/**
 * Excel异常
 * @author zhangyj
 *
 */
public class ExcelException extends BaseException{

	private static final long serialVersionUID = 3240288821877252548L;

	public ExcelException() {
		super();
	}

	public ExcelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ExcelException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}
	
	
	
}
