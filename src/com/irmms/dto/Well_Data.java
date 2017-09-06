package com.irmms.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "well_data")
@XmlType (propOrder={"well_parts_data","rec_maintenance_spares"})
public class Well_Data {
	
	private List<WellPartsDTO> well_parts_data;
	
	private List<RecMaintenanceSparesDTO> rec_maintenance_spares;
	
	private List<WellPartsHistoryDTO> parts_history;
	
	@XmlElementWrapper(name = "well_parts_list")
    @XmlElement
	public List<WellPartsDTO> getWell_parts_data() {
		return well_parts_data;
	}

	public void setWell_parts_data(List<WellPartsDTO> well_parts_data) {
		this.well_parts_data = well_parts_data;
	}

	@XmlElementWrapper(name = "Rec_MaintenanceSpares_list")
	@XmlElement
	public List<RecMaintenanceSparesDTO> getRec_maintenance_spares() {
		return rec_maintenance_spares;
	}

	public void setRec_maintenance_spares(
			List<RecMaintenanceSparesDTO> rec_maintenance_spares) {
		this.rec_maintenance_spares = rec_maintenance_spares;
	}

//	@XmlElementWrapper(name = "parts_history_list")
//	@XmlElement
//	public List<IBWell_Parts_HistoryDTO> getParts_history() {
//		return parts_history;
//	}
//
//	public void setParts_history(List<IBWell_Parts_HistoryDTO> parts_history) {
//		this.parts_history = parts_history;
//	}	
	
	
}
