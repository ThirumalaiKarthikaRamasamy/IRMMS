package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "recommended_spares_list")
@XmlType (propOrder={"recommended_spares","part_number","quantity"})

public class RecMaintenanceSparesDTO {
	
	private String recommended_spares;
	private String part_number;
	private String quantity;
	
	@XmlElement(name="recommended_spares")
	public String getRecommended_spares() {
		return recommended_spares;
	}
	public void setRecommended_spares(String recommended_spares) {
		this.recommended_spares = recommended_spares;
	}
	
	@XmlElement(name="part_number")
	public String getPart_number() {
		return part_number;
	}
	public void setPart_number(String part_number) {
		this.part_number = part_number;
	}
	
	@XmlElement(name="quantity")
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	

}
