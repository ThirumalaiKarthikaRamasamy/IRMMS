package com.irmms.exception;

public class MVTException extends MVTRunTimeException {

	
	/**
	 * Default serialVersionID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param errorCode
	 * @param errorParams
	 */
	public MVTException(String errorCode, String strErrMsg,
			Exception objException) {
		super(errorCode, strErrMsg, objException);
	}

}
