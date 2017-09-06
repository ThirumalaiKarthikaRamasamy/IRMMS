package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.CustomerDTO;
import com.irmms.util.StringUtil;


public class CustomerMapper implements RowMapper<CustomerDTO> {

	@Override
	public CustomerDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		
		CustomerDTO customerDTO = new CustomerDTO();

		customerDTO.setCustomer_name(StringUtil.parseString(rs.getString("CUSTOMER_NAME")));
		
		return customerDTO;
	}

}
