package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.WellDTO;
import com.irmms.util.StringUtil;

public class WellMapper implements RowMapper<WellDTO> {

	@Override
	public WellDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		WellDTO irmmsWellDTO = new WellDTO();
		
		irmmsWellDTO.setWell_id(StringUtil.parseString(rs.getString("WELL_ID")));
		irmmsWellDTO.setCustomer_name(StringUtil.parseString(rs.getString("CUSTOMER_NAME")));
		irmmsWellDTO.setWell_name(StringUtil.parseString(rs.getString("WELL_NAME")));
		irmmsWellDTO.setField(StringUtil.parseString(rs.getString("FIELD")));
		irmmsWellDTO.setBlock_no(StringUtil.parseString(rs.getString("BLOCK_NO")));
		irmmsWellDTO.setLatitude(StringUtil.parseString(rs.getString("LATITUDE")));
		irmmsWellDTO.setLongitude(StringUtil.parseString(rs.getString("LONGITUDE")));
		irmmsWellDTO.setMaintenance_status(StringUtil.parseString(rs.getString("mstatus")));
		
		return irmmsWellDTO;
	}

}
