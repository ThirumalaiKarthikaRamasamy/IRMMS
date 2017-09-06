package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product_description")
@XmlType (propOrder={"product_description"})
public class XMASDTO {
	private String product_description;

	@XmlElement (name="product_description")
	public String getProduct_description() {
		return product_description;
	}

	public void setProduct_description(String product_description) {
		this.product_description = product_description;
	}

}
