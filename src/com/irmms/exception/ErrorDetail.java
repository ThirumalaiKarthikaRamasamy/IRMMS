package com.irmms.exception;

import java.io.Serializable;


public class ErrorDetail implements Serializable {
	/**
	 * Default serialversionid
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Declare an error code
	 */
	private String errorCode;
	/**
	 * Declare an error Message
	 */
	private String errorMessage;
	/**
	 * Declare errorObject
	 */
	private Exception objException;

	/**
	 * Constructor for this class
	 */
	public ErrorDetail() {
	}

	/**
	 * @param errorCode
	 */
	public ErrorDetail(String errorCode) {
		this(errorCode, "");
	}

	/**
	 * @param errorCode
	 * @param params
	 */
	public ErrorDetail(String errorCode, String strErrMsg,
			Exception objException) {
		this.errorCode = errorCode;
		this.errorMessage = strErrMsg;
		this.objException = objException;
	}

	/**
	 * @param errorCode
	 * @param params
	 */
	public ErrorDetail(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @author:
	 * @return String
	 * @Description: return errorcode
	 */
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @return the objException
	 */
	public Exception getObjException() {
		return objException;
	}

	/**
	 * @param objException
	 *            the objException to set
	 */
	public void setObjException(Exception objException) {
		this.objException = objException;
	}
}
