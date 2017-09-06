package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.ExportWellDTO;
import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.util.StringUtil;

public class ExportWellMapper implements RowMapper<ExportWellDTO> {

	@Override
	public ExportWellDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ExportWellDTO irmmsExportWellDTO = new ExportWellDTO();
		
		irmmsExportWellDTO.setWELL_ID(StringUtil.parseString(rs.getString("WELL_ID")));
		irmmsExportWellDTO.setCUSTOMER_NAME(StringUtil.parseString(rs.getString("CUSTOMER_NAME")));
		irmmsExportWellDTO.setWELL_NAME(StringUtil.parseString(rs.getString("WELL_NAME")));
		irmmsExportWellDTO.setFIELD(StringUtil.parseString(rs.getString("FIELD")));
		irmmsExportWellDTO.setBLOCK_NO(StringUtil.parseString(rs.getString("BLOCK_NO")));
		irmmsExportWellDTO.setLATITUDE(StringUtil.parseString(rs.getString("LATITUDE")));
		irmmsExportWellDTO.setLONGITUDE(StringUtil.parseString(rs.getString("LONGITUDE")));
		irmmsExportWellDTO.setPART_NO(StringUtil.parseString(rs.getString("PART_NO")));
		irmmsExportWellDTO.setSERIAL_NO(StringUtil.parseString(rs.getString("SERIAL_NO")));
		irmmsExportWellDTO.setPRODUCT_DESCRIPTION(StringUtil.parseString(rs.getString("PRODUCT_DESCRIPTION")));
		irmmsExportWellDTO.setPMS_DESCRIPTION(StringUtil.parseString(rs.getString("PMS_DESCRIPTION")));
		//irmmsExportWellDTO.setSHIPPED_SERVICE_DATE(StringUtil.parseString(rs.getString("SHIPPED_SERVICE_DATE")));
		irmmsExportWellDTO.setLAST_MAINTENANCE_DATE(StringUtil.parseString(rs.getString("LAST_MAINTENANCE_DATE")));
		irmmsExportWellDTO.setNEXT_MAINTENANCE_DATE(StringUtil.parseString(rs.getString("NEXT_MAINTENANCE_DATE")));
		//irmmsExportWellDTO.setQUANTITY(StringUtil.parseString(rs.getString("QUANTITY")));
		//irmmsExportWellDTO.setPRICE(StringUtil.parseString(rs.getString("PRICE")));
		//irmmsExportWellDTO.setSALES_ORDER_TICKET_NO(StringUtil.parseString(rs.getString("SALES_ORDER_TICKET_NO")));
		irmmsExportWellDTO.setMstatus(StringUtil.parseString(rs.getString("mstatus")));
		
		return irmmsExportWellDTO;
	}

}
