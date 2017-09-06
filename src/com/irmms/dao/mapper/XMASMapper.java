package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.XMASDTO;
import com.irmms.util.StringUtil;

public class XMASMapper implements RowMapper<XMASDTO> {

	@Override
	public XMASDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		
		XMASDTO xmasDTO = new XMASDTO();

		xmasDTO.setProduct_description(StringUtil.parseString(rs.getString("PRODUCT_DESCRIPTION")));
		

		return xmasDTO;
	}

}
