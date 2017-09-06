package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.util.StringUtil;



public class RecMaintenanceMapper implements RowMapper<RecMaintenanceDTO> {

	@Override
	public RecMaintenanceDTO mapRow(ResultSet rs, int arg1)
			throws SQLException {
		
		RecMaintenanceDTO irmmsRec_MaintenanceDTO = new RecMaintenanceDTO();
		
		irmmsRec_MaintenanceDTO.setRecommended_maintenance(StringUtil.parseString(rs.getString("RECOMMENDED_MAINTENANCE")));
		irmmsRec_MaintenanceDTO.setMaintenance_desc(StringUtil.parseString(rs.getString("MAINTENANCE_DESC")));
		irmmsRec_MaintenanceDTO.setMaintenance_id(rs.getString("MAINTENANCE_ID"));
		
		return irmmsRec_MaintenanceDTO;
	}

}
