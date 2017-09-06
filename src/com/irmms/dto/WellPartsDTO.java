package com.irmms.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "well_parts_data")
@XmlType (propOrder={"part_no","serial_no","product_description","mstatus","last_maintenance_date","next_maintenance_date","perwell_parts_history_list"})

public class WellPartsDTO implements Comparable<WellPartsDTO> {
	
	private String part_no;
	private String serial_no;
	private String product_description;
	private String pms_description;
	private String mstatus;
	private String last_maintenance_date;
	private String next_maintenance_date;
	private int order;
	private List<WellPartsHistoryDTO> perwell_parts_history_list = null;
	
	@XmlElement
	public String getLast_maintenance_date() {
		return last_maintenance_date;
	}
	public void setLast_maintenance_date(String last_maintenance_date) {
		this.last_maintenance_date = last_maintenance_date;
	}
	
	@XmlElement
	public String getNext_maintenance_date() {
		return next_maintenance_date;
	}
	public void setNext_maintenance_date(String next_maintenance_date) {
		this.next_maintenance_date = next_maintenance_date;
	}

	
	
	@XmlElement (name="part_no")
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	
	@XmlElement (name="serial_no")
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	@XmlElement (name="product_description")
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	@XmlTransient
	public String getPms_description() {
		return pms_description;
	}
	public void setPms_description(String pms_description) {
		this.pms_description = pms_description;
	}
	
	@XmlElement (name="maintenance_status")
	public String getMstatus() {
		return mstatus;
	}
	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}
	
	@XmlTransient
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	@Override
	public int compareTo(WellPartsDTO parts) {
		
		if(this.order<parts.order)
			return -1;
		else if(this.order>parts.order)
			return 1;
		else
			return 0;
	}
	
	public void setPerwell_parts_history_list(
			List<WellPartsHistoryDTO> perwell_parts_history_list) {
		this.perwell_parts_history_list = perwell_parts_history_list;
	}
	@XmlElementWrapper(name = "parts_history_list")
	@XmlElement(name="parts_history")
	public List<WellPartsHistoryDTO> getPerwell_parts_history_list() {
		return perwell_parts_history_list;
	}	

}
