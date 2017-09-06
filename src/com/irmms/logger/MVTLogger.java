package com.irmms.logger;

import java.io.Serializable;

import org.apache.log4j.Logger;

public class MVTLogger implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7207912030611561159L;
	/** The log. */
	private Logger log;

	/**
	 * Gets the logger.
	 * 
	 * @param className the class name
	 * 
	 * @return the logger
	 */
	public static MVTLogger getLogger(final Class<?> className) {
		MVTLogger logger = new MVTLogger();
		logger.setLog(Logger.getLogger(className));
		return logger;
	}

	public void setLog(Logger log) {
		this.log=log;
	}
	public Logger getLog() {
		return log;
	}
	/**
	 * void.
	 * 
	 * @param obj the obj
	 */
	public void info(Object obj) {
		log.info(" >>>>> "+obj);
	}
	/**
	 * void.
	 * 
	 * @param obj the obj
	 * @param throwable the throwable
	 */

	public void info(Object obj, Throwable throwable) {
		log.warn(obj, throwable);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 */
	public void warn(Object obj) {
		log.warn(obj);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 * @param throwable the throwable
	 */
	public void warn(Object obj, Throwable throwable) {
		log.warn(obj, throwable);
	}
	/**
	 * void.
	 * 
	 * @param obj the obj
	 */
	public void debug(Object obj) {
		log.debug(obj);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 * @param throwable the throwable
	 */
	public void debug(Object obj, Throwable throwable) {
		log.debug(obj, throwable);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 */
	public void error(Object obj) {
		log.error(obj);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 * @param throwable the throwable
	 */
	public void error(Object obj, Throwable throwable) {
		log.error(obj, throwable);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 */
	public void fatal(Object obj) {
		log.fatal(obj);
	}

	/**
	 * void.
	 * 
	 * @param obj the obj
	 * @param throwable the throwable
	 */
	public void fatal(Object obj, Throwable throwable) {
		log.fatal(obj, throwable);
	}

}
