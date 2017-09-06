package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.WellPartsHistoryDTO;
import com.irmms.util.StringUtil;

public class WellPartsHistoryMapper implements RowMapper<WellPartsHistoryDTO> {

	@Override
	public WellPartsHistoryDTO mapRow(ResultSet rs, int arg1)
			throws SQLException {
		WellPartsHistoryDTO irmmsWell_Parts_HistoryDTO = new WellPartsHistoryDTO();
		
		irmmsWell_Parts_HistoryDTO.setWell_id(StringUtil.parseString(rs.getString("WELL_ID")));
		irmmsWell_Parts_HistoryDTO.setWell_name(StringUtil.parseString(rs.getString("WELL_NAME")));
		irmmsWell_Parts_HistoryDTO.setProduct_description(StringUtil.parseString(rs.getString("product_description")));
		irmmsWell_Parts_HistoryDTO.setPart_no(StringUtil.parseString(rs.getString("part_no")));
		irmmsWell_Parts_HistoryDTO.setSerial_no(StringUtil.parseString(rs.getString("serial_no")));
		irmmsWell_Parts_HistoryDTO.setUpdate_by(StringUtil.parseString(rs.getString("last_updated_by")));
		irmmsWell_Parts_HistoryDTO.setUpdate_date(StringUtil.parseString(rs.getString("last_updated_date")));	
		
		return irmmsWell_Parts_HistoryDTO;
	}

}
