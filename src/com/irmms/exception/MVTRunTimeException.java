package com.irmms.exception;

public class MVTRunTimeException  extends RuntimeException{

	
	/**
	 * serialversionid of this class
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Cause of exception
	 */
	private Throwable cause;
	/**
	 * Declare error details for adding error code and error params
	 */
	private ErrorDetail errorDetail;

	/**
	 * Declare error details for adding error code and error params
	 */
	/**
	 * Constructor of RMDRunTimeException
	 */
	public MVTRunTimeException() {
		super();
	}

	/**
	 * @param msg
	 */
	public MVTRunTimeException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public MVTRunTimeException(Throwable cause) {
		this(cause, null);
	}

	/**
	 * @param cause
	 * @param msg
	 */
	public MVTRunTimeException(Throwable cause, String msg) {
		super(msg);
		this.cause = cause;
	}
	/**
	 * @param errorCode
	 * @param params
	 */
	public MVTRunTimeException(String errorCode, String strErrMsg,
			Exception objException) {
		super("Error Code is ::- " + errorCode + " Error Message : "
				+ objException.getMessage());
		ErrorDetail errorDetailVO = new ErrorDetail(errorCode, strErrMsg,
				objException);
		this.setErrorDetail(errorDetailVO);
		//objException.printStackTrace();
	}

	/**
	 * @author:
	 * @param errorDetail
	 * @Description: Set errordetail object
	 */
	public final void setErrorDetail(ErrorDetail errorDetail) {
		this.errorDetail = errorDetail;
	}

	/**
	 * @author:
	 * @return ErrorDetail
	 * @Description: Get errordetail object
	 */
	public ErrorDetail getErrorDetail() {
		return errorDetail;
	}

	/**
	 * @param errorDetail
	 */
	public MVTRunTimeException(ErrorDetail errorDetail) {
		this.errorDetail = errorDetail;
	}

}
