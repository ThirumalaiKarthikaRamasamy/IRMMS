package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Well_Rec_Parts")
public class WellRecMaintenanceDTO {
	
	private String well_id;
	private String well_name;
	private String product_description;
	private String last_maintenanc_date;
	private String next_maintenanc_date;
	
	public String getWell_id() {
		return well_id;
	}
	
	@XmlElement
	public void setWell_id(String well_id) {
		this.well_id = well_id;
	}
	
	public String getWell_name() {
		return well_name;
	}
	
	@XmlElement
	public void setWell_name(String well_name) {
		this.well_name = well_name;
	}
	
	public String getProduct_description() {
		return product_description;
	}
	
	@XmlElement
	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}
	
	public String getLast_maintenanc_date() {
		return last_maintenanc_date;
	}
	
	@XmlElement
	public void setLast_maintenanc_date(String last_maintenanc_date) {
		this.last_maintenanc_date = last_maintenanc_date;
	}
	
	public String getNext_maintenanc_date() {
		return next_maintenanc_date;
	}
	
	@XmlElement
	public void setNext_maintenanc_date(String next_maintenanc_date) {
		this.next_maintenanc_date = next_maintenanc_date;
	}
	
	public WellRecMaintenanceDTO(String well_id ,String well_name, String product_description,
			String last_maintenanc_date, String next_maintenanc_date) {
		super();
		this.well_id = well_id;
		this.well_name = well_name;
		this.product_description = product_description;
		this.last_maintenanc_date = last_maintenanc_date;
		this.next_maintenanc_date = next_maintenanc_date;
	}
	
	public WellRecMaintenanceDTO()
	{
		
	}	

	
	
}
