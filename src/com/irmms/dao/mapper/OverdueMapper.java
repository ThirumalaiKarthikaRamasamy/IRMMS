package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.OverdueDTO;
import com.irmms.dto.WellDTO;
import com.irmms.util.StringUtil;

public class OverdueMapper implements RowMapper<OverdueDTO> {
	
	@Override
	public OverdueDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		OverdueDTO irmmsOverdueDTO = new OverdueDTO();
		
		irmmsOverdueDTO.setWELL_NAME(StringUtil.parseString(rs.getString("WELL_NAME")));
		irmmsOverdueDTO.setPART_NO(StringUtil.parseString(rs.getString("part_no")));
		irmmsOverdueDTO.setNEXT_MAINTENANCE_DATE(StringUtil.parseString(rs.getString("next_maintenance_date")));
		irmmsOverdueDTO.setDAYS(StringUtil.parseString(rs.getString("days")));
		
		return irmmsOverdueDTO;
	}

}
