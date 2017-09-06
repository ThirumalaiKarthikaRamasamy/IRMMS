package com.irmms.dto;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "well_data")
@XmlType (propOrder={"search_wells_data","customer_name_data"})
public class WellSearchData {
	
private List<WellDTO> search_wells_data;
	
private List<CustomerNameDTO> customer_name_data;

	@XmlElementWrapper(name = "search_well_data")
    @XmlElement
    public List<WellDTO> getSearch_wells_data() {
		return search_wells_data;
	}

	public void setSearch_wells_data(List<WellDTO> search_wells_data) {
		this.search_wells_data = search_wells_data;
	}

	@XmlElementWrapper(name = "customer_names_data")
	@XmlElement
	public List<CustomerNameDTO> getCustomer_name_data() {
		return customer_name_data;
	}

	public void setCustomer_name_data(List<CustomerNameDTO> customer_name_data) {
		this.customer_name_data = customer_name_data;
	}

}
