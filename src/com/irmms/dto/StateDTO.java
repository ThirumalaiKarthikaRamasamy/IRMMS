package com.irmms.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SHIP")
public class StateDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private String strStateName;
	private String strNumOfWell;
	private String strLATITUDE;
	private String strLONGITUDE;
	/**
	 * @return the strStateName
	 */
	@XmlElement (name="STATE_NAME")
	public String getStrStateName() {
		return strStateName;
	}
	/**
	 * @param strStateName the strStateName to set
	 */
	public void setStrStateName(String strStateName) {
		this.strStateName = strStateName;
	}
	/**
	 * @return the strNumOfWell
	 */
	@XmlElement (name="NO_OF_WELLS")
	public String getStrNumOfWell() {
		return strNumOfWell;
	}
	/**
	 * @param strNumOfWell the strNumOfWell to set
	 */
	public void setStrNumOfWell(String strNumOfWell) {
		this.strNumOfWell = strNumOfWell;
	}
	/**
	 * @return the strLATITUDE
	 */
	@XmlElement (name="LATITUDE")
	public String getStrLATITUDE() {
		return strLATITUDE;
	}
	/**
	 * @param strLATITUDE the strLATITUDE to set
	 */
	public void setStrLATITUDE(String strLATITUDE) {
		this.strLATITUDE = strLATITUDE;
	}
	/**
	 * @return the strLONGITUDE
	 */
	@XmlElement (name="LONGITUDE")
	public String getStrLONGITUDE() {
		return strLONGITUDE;
	}
	/**
	 * @param strLONGITUDE the strLONGITUDE to set
	 */
	public void setStrLONGITUDE(String strLONGITUDE) {
		this.strLONGITUDE = strLONGITUDE;
	}
	
	

	

}
