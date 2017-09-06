package com.irmms.services;


import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.core.Response;

import com.irmms.dto.CustomerDTO;
import com.irmms.dto.OverdueDTO;
import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.dto.RecMaintenanceSparesDTO;
import com.irmms.dto.RegionDataDTO;
import com.irmms.dto.WellDTO;
import com.irmms.dto.WellPartsDTO;
import com.irmms.dto.WellPartsEditDTO;
import com.irmms.dto.WellPartsHistoryDTO;
import com.irmms.dto.WellRecMaintenanceDTO;
import com.irmms.dto.WellSearchData;

public interface MVTServiceIntf {
		
	public List<WellDTO> getWellData();
	
	public List<WellPartsDTO> getWellPartsData(String well_id);
	
	public List<RecMaintenanceDTO> getRec_Maintenance();
	
	public List<RecMaintenanceSparesDTO> getRec_MaintenanceSpares(String well_name);
	
	public String editwellparts(CopyOnWriteArrayList<WellPartsEditDTO> irmmsWellPartsEditDTOlst);
	
	public String editmaintenance(CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst);
	
	public List<WellPartsHistoryDTO> getpartshistory(String well_id);
	
	public String getwell_name(String well_id);
	
	public List<RegionDataDTO> getregion();
	
	public List<WellDTO> getWellbyregion(String region);
	
	public Response getexportwell(String wellid);

	public List<CustomerDTO> getCustomer();

	public WellSearchData getSearchData(String data);

	public String uploadWellData(InputStream stream);
	
	public List<OverdueDTO> getoverdue();
}
