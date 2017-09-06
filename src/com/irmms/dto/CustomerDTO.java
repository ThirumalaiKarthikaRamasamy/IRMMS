package com.irmms.dto;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "customer_data")
@XmlType (propOrder={"customer_name"})

public class CustomerDTO {

	private String customer_name;

	@XmlElement (name="customer_name")
	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
}
