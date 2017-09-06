package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "wells_data")
@XmlType (propOrder={"well_id","customer_name","well_name","field","block_no","latitude","longitude","maintenance_status"})
public class WellDTO {
	
	private String well_id;
	private String customer_name;
	private String well_name;
	private String field;
	private String block_no;
	private String latitude;
	private String longitude;
	private String maintenance_status;
	
	@XmlElement (name="well_id")
	public String getWell_id() {
		return well_id;
	}
	
	public void setWell_id(String well_id) {
		this.well_id = well_id;
	}
	
	@XmlElement (name="customer_name")
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	@XmlElement (name="well_name")
	public String getWell_name() {
		return well_name;
	}
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	@XmlElement (name="field")
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	@XmlElement (name="block_no")
	public String getBlock_no() {
		return block_no;
	}
	public void setBlock_no(String block_no) {
		this.block_no = block_no;
	}
	
	@XmlElement (name="latitude")
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@XmlElement (name="longitude")
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@XmlElement (name="maintenance_status")
	public String getMaintenance_status() {
		return maintenance_status;
	}
	public void setMaintenance_status(String maintenance_status) {
		this.maintenance_status = maintenance_status;
	}
	
	
	

}
