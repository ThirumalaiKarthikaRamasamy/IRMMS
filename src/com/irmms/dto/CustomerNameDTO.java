package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "customer_name_list")
@XmlType (propOrder={"customerName"})

public class CustomerNameDTO {
	
	private String customerName;

	@XmlElement(name="customerName")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


}
