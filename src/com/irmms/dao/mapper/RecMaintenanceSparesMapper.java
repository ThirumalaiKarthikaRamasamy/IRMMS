package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.RecMaintenanceSparesDTO;
import com.irmms.util.StringUtil;

public class RecMaintenanceSparesMapper implements RowMapper<RecMaintenanceSparesDTO> {

	@Override
	public RecMaintenanceSparesDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		
		RecMaintenanceSparesDTO irmmsRec_MaintenanceSparesDTO = new RecMaintenanceSparesDTO();
		
		irmmsRec_MaintenanceSparesDTO.setRecommended_spares(StringUtil.parseString(rs.getString("RECOMMENDED_SPARES")));
		irmmsRec_MaintenanceSparesDTO.setPart_number(StringUtil.parseString(rs.getString("PART_NUMBER")));
		irmmsRec_MaintenanceSparesDTO.setQuantity(StringUtil.parseString(rs.getString("QUANTITY")));
		
		return irmmsRec_MaintenanceSparesDTO;
		
	}		

}
