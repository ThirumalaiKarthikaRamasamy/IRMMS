package com.irmms.dto;

import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlElement;

@XmlRootElement(name = "well_export_data")

public class WellExportData {
	
	private String WELL_ID;
	private String CUSTOMER_NAME;
	private String WELL_NAME;
	private String FIELD;
	private String BLOCK_NO;
	private String LATITUDE;
	private String LONGITUDE;
	private String PART_NO;
	private String SERIAL_NO;
	private String PRODUCT_DESCRIPTION;
	private String PMS_DESCRIPTION;
	private String SHIPPED_SERVICE_DATE;
	private String LAST_MAINTENANCE_DATE;
	private String NEXT_MAINTENANCE_DATE;
	private String QUANTITY;
	private String PRICE;
	private String SALES_ORDER_TICKET_NO;
	private String mstatus;
	
	@XmlElement
	public String getWELL_ID() {
		return WELL_ID;
	}
	public void setWELL_ID(String wELL_ID) {
		WELL_ID = wELL_ID;
	}
	@XmlElement
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}
	@XmlElement
	public String getWELL_NAME() {
		return WELL_NAME;
	}
	public void setWELL_NAME(String wELL_NAME) {
		WELL_NAME = wELL_NAME;
	}
	@XmlElement
	public String getFIELD() {
		return FIELD;
	}
	public void setFIELD(String fIELD) {
		FIELD = fIELD;
	}
	@XmlElement
	public String getBLOCK_NO() {
		return BLOCK_NO;
	}
	public void setBLOCK_NO(String bLOCK_NO) {
		BLOCK_NO = bLOCK_NO;
	}
	@XmlElement
	public String getLATITUDE() {
		return LATITUDE;
	}
	public void setLATITUDE(String lATITUDE) {
		LATITUDE = lATITUDE;
	}
	@XmlElement
	public String getLONGITUDE() {
		return LONGITUDE;
	}
	public void setLONGITUDE(String lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}
	@XmlElement
	public String getPART_NO() {
		return PART_NO;
	}
	public void setPART_NO(String pART_NO) {
		PART_NO = pART_NO;
	}
	@XmlElement
	public String getSERIAL_NO() {
		return SERIAL_NO;
	}
	public void setSERIAL_NO(String sERIAL_NO) {
		SERIAL_NO = sERIAL_NO;
	}
	@XmlElement
	public String getPRODUCT_DESCRIPTION() {
		return PRODUCT_DESCRIPTION;
	}
	public void setPRODUCT_DESCRIPTION(String pRODUCT_DESCRIPTION) {
		PRODUCT_DESCRIPTION = pRODUCT_DESCRIPTION;
	}
	@XmlElement
	public String getPMS_DESCRIPTION() {
		return PMS_DESCRIPTION;
	}
	public void setPMS_DESCRIPTION(String pMS_DESCRIPTION) {
		PMS_DESCRIPTION = pMS_DESCRIPTION;
	}
	@XmlElement
	public String getSHIPPED_SERVICE_DATE() {
		return SHIPPED_SERVICE_DATE;
	}
	public void setSHIPPED_SERVICE_DATE(String sHIPPED_SERVICE_DATE) {
		SHIPPED_SERVICE_DATE = sHIPPED_SERVICE_DATE;
	}
	@XmlElement
	public String getLAST_MAINTENANCE_DATE() {
		return LAST_MAINTENANCE_DATE;
	}
	public void setLAST_MAINTENANCE_DATE(String lAST_MAINTENANCE_DATE) {
		LAST_MAINTENANCE_DATE = lAST_MAINTENANCE_DATE;
	}
	@XmlElement
	public String getNEXT_MAINTENANCE_DATE() {
		return NEXT_MAINTENANCE_DATE;
	}
	public void setNEXT_MAINTENANCE_DATE(String nEXT_MAINTENANCE_DATE) {
		NEXT_MAINTENANCE_DATE = nEXT_MAINTENANCE_DATE;
	}
	@XmlElement
	public String getQUANTITY() {
		return QUANTITY;
	}
	public void setQUANTITY(String qUANTITY) {
		QUANTITY = qUANTITY;
	}
	@XmlElement
	public String getPRICE() {
		return PRICE;
	}
	public void setPRICE(String pRICE) {
		PRICE = pRICE;
	}
	@XmlElement
	public String getSALES_ORDER_TICKET_NO() {
		return SALES_ORDER_TICKET_NO;
	}
	public void setSALES_ORDER_TICKET_NO(String sALES_ORDER_TICKET_NO) {
		SALES_ORDER_TICKET_NO = sALES_ORDER_TICKET_NO;
	}
	@XmlElement
	public String getMstatus() {
		return mstatus;
	}
	public void setMstatus(String mstatus) {
		this.mstatus = mstatus;
	}	
}
