package com.irmms.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "RECOMMENDED_MAINTENANCE")
@XmlType (propOrder={"recommended_maintenance","maintenance_desc","tools"})
public class RecMaintenanceDTO {
	
	private String recommended_maintenance;
	private String maintenance_desc;
	private String maintenance_id;
	
	private List<String> tools;
	
	@XmlElement(name = "RECOMMENDED_MAINTENANCE_NAME")
	public String getRecommended_maintenance() {
		return recommended_maintenance;
	}
	public void setRecommended_maintenance(String recommended_maintenance) {
		this.recommended_maintenance = recommended_maintenance;
	}
	
	@XmlElement(name = "MAINTENANCE_DESC")
	public String getMaintenance_desc() {
		return maintenance_desc;
	}
	public void setMaintenance_desc(String maintenance_desc) {
		this.maintenance_desc = maintenance_desc;
	}
	
	@XmlElementWrapper(name = "TOOLSLIST")	
	@XmlElement(name = "TOOLS")	
	public List<String> getTools() {
		return tools;
	}
	public void setTools(List<String> tools) {
		this.tools = tools;
	}
	
	@XmlTransient
	public String getMaintenance_id() {
		return maintenance_id;
	}
	public void setMaintenance_id(String maintenance_id) {
		this.maintenance_id = maintenance_id;
	}
	
	
}
