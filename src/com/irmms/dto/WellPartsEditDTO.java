package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Well_Parts")
public class WellPartsEditDTO {
	
	private String well_id;
	private String well_name;
	private String product_description;
	private String part_no;
	private String serial_no;
	
	
	public String getWell_id() {
		return well_id;
	}
	@XmlElement
	public void setWell_id(String well_id) {
		this.well_id = well_id;
	}
	
	public String getProduct_description() {
		return product_description;
	}
	@XmlElement
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	public String getPart_no() {
		return part_no;
	}
	@XmlElement
	public void setPart_no(String part_no) {
		this.part_no = part_no;
	}
	
	public String getSerial_no() {
		return serial_no;
	}
	@XmlElement
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	
	
	
	public String getWell_name() {
		return well_name;
	}
	@XmlElement
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	public WellPartsEditDTO(String well_id ,String well_name, String product_description,
			String part_no, String serial_no) {
		super();
		this.well_id = well_id;
		this.well_name = well_name;
		this.product_description = product_description;
		this.part_no = part_no;
		this.serial_no = serial_no;
	}
	
	public WellPartsEditDTO()
	{
		
	}	
		
	}
