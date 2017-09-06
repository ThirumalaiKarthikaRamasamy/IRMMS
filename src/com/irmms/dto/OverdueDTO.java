package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "overdue_data")
@XmlType (propOrder={"WELL_NAME","PART_NO","NEXT_MAINTENANCE_DATE","DAYS"})
public class OverdueDTO {

	private String WELL_NAME;
	private String PART_NO;
	private String NEXT_MAINTENANCE_DATE;
	private String DAYS;
	
	@XmlElement
	public String getWELL_NAME() {
		return WELL_NAME;
	}
	public void setWELL_NAME(String wELL_NAME) {
		WELL_NAME = wELL_NAME;
	}
	@XmlElement
	public String getPART_NO() {
		return PART_NO;
	}
	public void setPART_NO(String pART_NO) {
		PART_NO = pART_NO;
	}
	@XmlElement
	public String getNEXT_MAINTENANCE_DATE() {
		return NEXT_MAINTENANCE_DATE;
	}
	public void setNEXT_MAINTENANCE_DATE(String nEXT_MAINTENANCE_DATE) {
		NEXT_MAINTENANCE_DATE = nEXT_MAINTENANCE_DATE;
	}
	@XmlElement
	public String getDAYS() {
		return DAYS;
	}
	public void setDAYS(String dAYS) {
		DAYS = dAYS;
	}
	
	
}
