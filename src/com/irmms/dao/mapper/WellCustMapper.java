package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.CustomerNameDTO;
import com.irmms.util.StringUtil;

public class WellCustMapper  implements RowMapper<CustomerNameDTO> {
	
	@Override
	public CustomerNameDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		CustomerNameDTO irmmsWellDTO = new CustomerNameDTO();
		
		irmmsWellDTO.setCustomerName(StringUtil.parseString(rs.getString("CUSTOMER_NAME")));
		
		return irmmsWellDTO;
	}

}
