/**
 * 
 */
package com.irmms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.irmms.dto.StateDTO;
import com.irmms.util.StringUtil;

/**
 * @author 502372676
 *
 */
public class StateMapper implements RowMapper<StateDTO>{

	@Override
	public StateDTO mapRow(ResultSet rs, int arg1) throws SQLException {
		StateDTO irmmsStateDTO = new StateDTO();
		
		irmmsStateDTO.setStrStateName(StringUtil.parseString(rs.getString("STATE_NAME")));
		irmmsStateDTO.setStrNumOfWell(StringUtil.parseString(rs.getString("NO_OF_WELLS")));
		irmmsStateDTO.setStrLATITUDE(StringUtil.parseString(rs.getString("LATITUDE")));
		irmmsStateDTO.setStrLONGITUDE(StringUtil.parseString(rs.getString("LONGITUDE")));
		
		return irmmsStateDTO;
	}

}
