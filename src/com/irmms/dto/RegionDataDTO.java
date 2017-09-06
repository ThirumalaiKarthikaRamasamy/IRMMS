package com.irmms.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "region_data")
@XmlType (propOrder={"group_region","no_of_weels","avg_latitude","avg_longitude"})
public class RegionDataDTO implements Comparable<RegionDataDTO>{
	
	private String group_region;
	private String no_of_weels;
	private String avg_latitude;
	private String avg_longitude;
	
	@XmlElement
	public String getGroup_region() {
		return group_region;
	}
	public void setGroup_region(String group_region) {
		this.group_region = group_region;
	}
	
	@XmlElement
	public String getNo_of_weels() {
		return no_of_weels;
	}
	public void setNo_of_weels(String no_of_weels) {
		this.no_of_weels = no_of_weels;
	}
	
	@XmlElement
	public String getAvg_latitude() {
		return avg_latitude;
	}
	public void setAvg_latitude(String avg_latitude) {
		this.avg_latitude = avg_latitude;
	}
	
	@XmlElement
	public String getAvg_longitude() {
		return avg_longitude;
	}
	public void setAvg_longitude(String avg_longitude) {
		this.avg_longitude = avg_longitude;
	}
	@Override
	public int compareTo(RegionDataDTO compare_obj) {
		return this.getGroup_region().compareTo(compare_obj.getGroup_region());
	}

}
