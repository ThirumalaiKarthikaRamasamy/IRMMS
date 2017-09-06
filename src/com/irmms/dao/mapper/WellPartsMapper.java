package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.WellPartsDTO;
import com.irmms.util.StringUtil;

public class WellPartsMapper implements RowMapper<WellPartsDTO> {

	@Override
	public WellPartsDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		
		WellPartsDTO irmmsWellPartsDTO = new WellPartsDTO();

		irmmsWellPartsDTO.setPart_no(StringUtil.parseString(rs.getString("part_no")));
		irmmsWellPartsDTO.setSerial_no(StringUtil.parseString(rs.getString("serial_no")));
		irmmsWellPartsDTO.setProduct_description(StringUtil.parseString(rs.getString("product_description")));
		irmmsWellPartsDTO.setPms_description(StringUtil.parseString(rs.getString("pms_description")));
		irmmsWellPartsDTO.setMstatus(StringUtil.parseString(rs.getString("mstatus")));
		irmmsWellPartsDTO.setLast_maintenance_date(StringUtil.parseString(rs.getString("last_maintenance_date")));
		irmmsWellPartsDTO.setNext_maintenance_date(StringUtil.parseString(rs.getString("next_maintenance_date")));
		
		if(irmmsWellPartsDTO.getProduct_description().contains("XMAS TREE"))
			irmmsWellPartsDTO.setOrder(1);
		else if(irmmsWellPartsDTO.getProduct_description().contains("TUBING HANGER"))
			irmmsWellPartsDTO.setOrder(2);
		else if(irmmsWellPartsDTO.getProduct_description().contains("MS-T Seal (Completion)"))
			irmmsWellPartsDTO.setOrder(3);
		else if(irmmsWellPartsDTO.getProduct_description().contains("MULTI-BOWL"))
			irmmsWellPartsDTO.setOrder(4);		
		else if(irmmsWellPartsDTO.getProduct_description().contains("MS-T Seal  ( 9-5/8”)"))
			irmmsWellPartsDTO.setOrder(5);
		else if(irmmsWellPartsDTO.getProduct_description().contains("Casing Hanger ( 9-5/8”)"))
			irmmsWellPartsDTO.setOrder(6);
		else
			irmmsWellPartsDTO.setOrder(7);

		return irmmsWellPartsDTO;
	}

}
