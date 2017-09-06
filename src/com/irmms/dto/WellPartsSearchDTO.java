package com.irmms.dto;

public class WellPartsSearchDTO {
	
	private String customer_name;
	private String well_name;
	private String part_no;
	private String serial_no;
	private String maintenance_date;
	private String product_description;
	private String criteria_status;
	private String fieldName;
	
	public String getCustomer_name() {
		return customer_name;
	}
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	public String getWell_name() {
		return well_name;
	}
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	public String getPart_no() {
		return part_no;
	}
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	public String getMaintenance_date() {
		return maintenance_date;
	}
	public void setMaintenance_date(String maintenance_date) {
		this.maintenance_date = maintenance_date;
	}
	
	public String getProduct_description() {
		return product_description;
	}
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	public String getCriteria_status() {
		return criteria_status;
	}
	public void setCriteria_status(String criteria_status) {
		this.criteria_status = criteria_status;
	}
	
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public WellPartsSearchDTO(String customer_name ,String well_name, String part_no, String serial_no, String maintenance_date, String product_description, String criteria_status, String fieldName) {
		super();
		this.customer_name = customer_name;
		this.well_name = well_name;
		this.part_no = part_no;
		this.serial_no = serial_no;
		this.maintenance_date = maintenance_date;
		this.product_description = product_description;
		this.criteria_status = criteria_status;
		this.fieldName = fieldName;
	}
	
	public WellPartsSearchDTO()
	{
		
	}	

}
