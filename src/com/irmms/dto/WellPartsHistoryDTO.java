package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name="well_parts_history")
@XmlType (propOrder={"part_no","serial_no","update_by","update_date"})
public class WellPartsHistoryDTO {
	
	private String well_id;
	private String well_name;
	private String product_description;
	private String part_no;
	private String serial_no;
	private String update_by;
	private String update_date;
	
	@XmlTransient
	public String getWell_id() {
		return well_id;
	}
	public void setWell_id(String well_id) {
		this.well_id = well_id;
	}
	
	@XmlTransient
	public String getWell_name() {
		return well_name;
	}
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	@XmlTransient
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	@XmlElement
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	
	@XmlElement
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	@XmlElement
	public String getUpdate_by() {
		return update_by;
	}
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	
	@XmlElement
	public String getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}	

}
