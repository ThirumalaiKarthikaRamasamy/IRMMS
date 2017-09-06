package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.RegionDataDTO;
import com.irmms.dto.WellDTO;
import com.irmms.util.StringUtil;

public class RegionMapper implements RowMapper<RegionDataDTO> {

	@Override
	public RegionDataDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		RegionDataDTO irmmsRegion_DataDTO = new RegionDataDTO();

		irmmsRegion_DataDTO.setGroup_region(StringUtil.parseString(rs.getString("group_region")));
		irmmsRegion_DataDTO.setNo_of_weels(StringUtil.parseString(rs.getString("no_of_wells")));
		irmmsRegion_DataDTO.setAvg_latitude(StringUtil.parseString(rs.getString("latitude")));
		irmmsRegion_DataDTO.setAvg_longitude(StringUtil.parseString(rs.getString("longitude")));

		return irmmsRegion_DataDTO;
	}

}
