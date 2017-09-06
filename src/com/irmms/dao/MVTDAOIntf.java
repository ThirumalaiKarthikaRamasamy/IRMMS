package com.irmms.dao;


import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.irmms.dto.CustomerDTO;
import com.irmms.dto.CustomerNameDTO;
import com.irmms.dto.ExportWellDTO;
import com.irmms.dto.OverdueDTO;
import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.dto.RecMaintenanceSparesDTO;
import com.irmms.dto.RegionDataDTO;
import com.irmms.dto.UploadDTO;
import com.irmms.dto.WellDTO;
import com.irmms.dto.WellPartsDTO;
import com.irmms.dto.WellPartsEditDTO;
import com.irmms.dto.WellPartsHistoryDTO;
import com.irmms.dto.WellPartsSearchDTO;
import com.irmms.dto.WellRecMaintenanceDTO;

public interface MVTDAOIntf {
	
	public List<WellDTO> getWellData();
	
	public List<WellPartsDTO> getWellPartsData(String well_id);
	
	public List<RecMaintenanceDTO> getRec_Maintenance();
	
	public List<RecMaintenanceSparesDTO> getRec_MaintenanceSpares(String well_name);
	
	public String editwellparts(CopyOnWriteArrayList<WellPartsEditDTO> wellPartsEditDTOlst);
	
	public String editmaintenance(CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst);
	
	public List<WellPartsHistoryDTO> getpartshistory(String well_id);
	
	public String getwell_name(String well_id);
	
	public List<RegionDataDTO> getregion();
	
	public List<WellDTO> getWellbyregion(String region);
	
	public List<ExportWellDTO> getexportwell(String wellid);

	public List<CustomerDTO> getCustomer();

	public List<WellDTO> searchWellData(WellPartsSearchDTO wellPartsSearchDTO);

	public List<CustomerNameDTO> searchCustomerData(WellPartsSearchDTO wellPartsSearchDTO);
	
	public String uploadDataImport(UploadDTO wellDtl);
	
	public List<OverdueDTO> getoverdue();
	
}
