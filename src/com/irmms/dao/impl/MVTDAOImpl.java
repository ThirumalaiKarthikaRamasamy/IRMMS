package com.irmms.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.irmms.dto.CustomerNameDTO;
import com.irmms.dao.MVTDAOIntf;
import com.irmms.dao.mapper.CustomerMapper;
import com.irmms.dao.mapper.ExportWellMapper;
import com.irmms.dao.mapper.OverdueMapper;
import com.irmms.dao.mapper.RecMaintenanceSparesMapper;
import com.irmms.dao.mapper.RegionMapper;
import com.irmms.dao.mapper.WellMapper;
import com.irmms.dao.mapper.WellPartsHistoryMapper;
import com.irmms.dao.mapper.WellPartsMapper;
import com.irmms.dto.CustomerDTO;
import com.irmms.dto.ExportWellDTO;
import com.irmms.dto.OverdueDTO;
import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.dto.RecMaintenanceSparesDTO;
import com.irmms.dto.RegionDataDTO;
import com.irmms.dto.UploadDTO;
import com.irmms.dto.WellDTO;
import com.irmms.dto.WellPartsDTO;
import com.irmms.dto.WellPartsEditDTO;
import com.irmms.dto.WellPartsHistoryDTO;
import com.irmms.dto.WellPartsSearchDTO;
import com.irmms.dto.WellRecMaintenanceDTO;
import com.irmms.exception.MVTException;
import com.irmms.logger.MVTLogger;
import com.irmms.dao.mapper.WellCustMapper;
import com.irmms.util.StringUtil;

@Service("mvtDAO")
@Transactional
public class MVTDAOImpl implements MVTDAOIntf {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private MVTLogger logger = MVTLogger.getLogger(this.getClass());

	DataSource dataSource;

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate
	 *            the jdbcTemplate to set
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource
	 *            the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<WellDTO> getWellData() {
		logger.info("getWellData  start in DAO ");

		List<WellDTO> lstWellData = new ArrayList<WellDTO>();

		String welldataQuery = "select Well_Data.WELL_ID as WELL_ID,Well_Data.CUSTOMER_NAME as CUSTOMER_NAME,Well_Data.WELL_NAME as WELL_NAME,Well_Data.FIELD as FIELD,Well_Data.BLOCK_NO as BLOCK_NO,"
				+ "Well_Data.LATITUDE as LATITUDE,Well_Data.LONGITUDE as LONGITUDE,Well_Color.mstatus as mstatus from (select distinct WELL_ID,CUSTOMER_NAME,WELL_NAME,FIELD,"
				+ "BLOCK_NO,LATITUDE,LONGITUDE from IRMMS_OIL_WELLS_DTL) Well_Data, (select WELL_ID, CASE "
				+ "when a.daterange is null then 'GR' "
				+ "WHEN a.daterange <0 THEN 'R' "
				+ "WHEN a.daterange<=30 THEN 'Y' "
				+ "ELSE 'G' END as mstatus from (select WELL_ID,(DATEDIFF(STR_TO_DATE((min(next_maintenance_date)), '%Y-%m-%d'),sysdate())) "
				+ "as daterange from IRMMS_OIL_WELLS_DTL group by WELL_ID)a) Well_Color where Well_Data.WELL_ID =  Well_Color.WELL_ID ";

		try {
			lstWellData = getJdbcTemplate().query(welldataQuery,
					new WellMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(welldataQuery, e.getMessage(), e);
		}

		logger.info("getWellData Query --- " + welldataQuery);
		return lstWellData;

	}

	@Override
	public List<WellPartsDTO> getWellPartsData(String well_id) {
		logger.info("getWELLPartsData  start in DAO ");

		List<WellPartsDTO> lstWellData = new ArrayList<WellPartsDTO>();

		String wellpartsdataQuery = "select part_no,serial_no,product_description,pms_description,"
				+ " date_format(last_maintenance_date,'%d-%m-%Y') last_maintenance_date,date_format(next_maintenance_date,'%d-%m-%Y') next_maintenance_date,CASE "
				+ "WHEN next_maintenance_date is null then 'GR' "
				+ "WHEN (DATEDIFF(STR_TO_DATE((irmms_oil_wells_dtl.next_maintenance_date), '%Y-%m-%d'),sysdate())) <0 THEN 'R' "
				+ "WHEN (DATEDIFF(STR_TO_DATE((irmms_oil_wells_dtl.next_maintenance_date), '%Y-%m-%d'),sysdate())) <=30 THEN 'Y' "
				+ "ELSE 'G' END as mstatus from IRMMS_OIL_WELLS_DTL where well_id='"
				+ well_id + "'";

		try {
			lstWellData = getJdbcTemplate().query(wellpartsdataQuery,
					new WellPartsMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(wellpartsdataQuery, e.getMessage(), e);
		}

		logger.info("getWellPartsData Query --- " + wellpartsdataQuery);
		return lstWellData;

	}
	
	@Override
	public List<RecMaintenanceDTO> getRec_Maintenance() {
	return null;
	}

/*	@Override
	public List<RecMaintenanceDTO> getRec_Maintenance() {
		logger.info("getRec_Maintenance  start in DAO ");

		List<RecMaintenanceDTO> Rec_MaintenanceData = new ArrayList<RecMaintenanceDTO>();

		String maintenance_data_Query = "select distinct MAINTENANCE_ID, RECOMMENDED_MAINTENANCE, MAINTENANCE_DESC from IRMMS_RECOMMENDED_MAIN order by maintenance_id";

		try {
			Rec_MaintenanceData = getJdbcTemplate().query(
					maintenance_data_Query, new RecMaintenanceMapper());
			
			List<String> Rec_MaintenanceTool = null;
			
			for(RecMaintenanceDTO irmmsRec_MaintenanceDTO:Rec_MaintenanceData)
			{
				Rec_MaintenanceTool = getRec_Tools(irmmsRec_MaintenanceDTO.getMaintenance_id());
				irmmsRec_MaintenanceDTO.setTools(Rec_MaintenanceTool);
			}
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(maintenance_data_Query, e.getMessage(), e);
		}

		return Rec_MaintenanceData;
	}*/

	/*public List<String> getRec_Tools(String maintenance_id) {

		logger.info("getRec_Tools  start in DAO ");

		List<String> Rec_MaintenanceTool;

		String maintenance_tool_Query = "select TOOLS from IRMMS_RECOMMEND_MAIN_TOOLS where maintenance_id =?";
		try {
			Rec_MaintenanceTool = getJdbcTemplate().queryForList(
					maintenance_tool_Query, new Object[] { maintenance_id },
					new int[] { java.sql.Types.VARCHAR }, String.class);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(maintenance_tool_Query, e.getMessage(), e);
		}

		return Rec_MaintenanceTool;
	}
*/
	@Override
	public List<RecMaintenanceSparesDTO> getRec_MaintenanceSpares(
			String well_name) {
		logger.info("IrmmsRec_MaintenanceSparesDTO  start in DAO ");

		List<RecMaintenanceSparesDTO> irmmsRec_MaintenanceSparesDTO_list;

		String maintenance_spears_Query = "select RECOMMENDED_SPARES,PART_NUMBER,QUANTITY from IRMMS_RECOMMENDED_SPARES where well_name=?";

		try {
			irmmsRec_MaintenanceSparesDTO_list = getJdbcTemplate().query(
					maintenance_spears_Query, new Object[] { well_name },
					new RecMaintenanceSparesMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(maintenance_spears_Query, e.getMessage(), e);
		}

		return irmmsRec_MaintenanceSparesDTO_list;
	}

	@Override
	public String editwellparts(
			final CopyOnWriteArrayList<WellPartsEditDTO> irmmsWellPartsEditDTOlst) {

		Map<String, WellPartsEditDTO> wellPartsEditMap = getWellPartsEditMap(irmmsWellPartsEditDTOlst
				.get(0).getWell_id());
		for (WellPartsEditDTO irmmsWellPartsEditDTO : irmmsWellPartsEditDTOlst) {
			
			if (!(("").equals(irmmsWellPartsEditDTO.getProduct_description())) 
					&& !(("").equals(irmmsWellPartsEditDTO.getPart_no())) 
							&& !(("").equals(irmmsWellPartsEditDTO.getSerial_no()))){
				
				if(StringUtil.parseString(irmmsWellPartsEditDTO.getPart_no()).equalsIgnoreCase(StringUtil.parseString(
					wellPartsEditMap.get(
							irmmsWellPartsEditDTO.getProduct_description())
							.getPart_no()))
					&&  StringUtil.parseString(irmmsWellPartsEditDTO.getSerial_no()).equalsIgnoreCase(
							StringUtil.parseString(wellPartsEditMap
									.get(irmmsWellPartsEditDTO
											.getProduct_description())
									.getSerial_no()))) {
				logger.info(":: in edit check if ::");
				irmmsWellPartsEditDTOlst.remove(irmmsWellPartsEditDTO);
				}
				
			}
			else
			{
				irmmsWellPartsEditDTOlst.remove(irmmsWellPartsEditDTO);
			}
			
		}

		logger.info("irmmsWellPartsEditDTOlst Final Size ::"
				+ irmmsWellPartsEditDTOlst.size());

		String Success_Fail = "Success";

		String well_parts_edit_Query = "update IRMMS_OIL_WELLS_DTL set PART_NO=?,SERIAL_NO=? where WELL_ID=? and PRODUCT_DESCRIPTION=?";

		String well_parts_history_Query = "insert into IRMMS_PARTS_HISTORY(WELL_ID,WELL_NAME,PRODUCT_DESCRIPTION,PART_NO,SERIAL_NO,LAST_UPDATED_BY,LAST_UPDATED_DATE)  values(?,?,?,?,?,?,sysdate())";

		if (irmmsWellPartsEditDTOlst.size() > 0) {
			logger.info(":: in Batch Update if ::");

			int[] trans_status = getJdbcTemplate().batchUpdate(
					well_parts_edit_Query, new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {

							WellPartsEditDTO irmmsWellPartsEditDTO = irmmsWellPartsEditDTOlst
									.get(i);
							System.out.println("Partno-"+irmmsWellPartsEditDTO.getPart_no()+"-"+"Serialno-"+irmmsWellPartsEditDTO.getSerial_no()+"-"+"wellid-"+irmmsWellPartsEditDTO.getWell_id()+"-"+"descrip-"+irmmsWellPartsEditDTO.getProduct_description());
							ps.setString(1, irmmsWellPartsEditDTO.getPart_no());
							ps.setString(2, irmmsWellPartsEditDTO.getSerial_no());
							ps.setString(3, irmmsWellPartsEditDTO.getWell_id());
							ps.setString(4,
									irmmsWellPartsEditDTO.getProduct_description());
						}

						@Override
						public int getBatchSize() {
							return irmmsWellPartsEditDTOlst.size();
						}

					});

			 for (int trans : trans_status) {
				if (trans == 0) {
					Success_Fail = "Fail";
					break;
				}
				else{
			if (Success_Fail.equalsIgnoreCase("Success")) {
				int[] insert_trans_status = getJdbcTemplate().batchUpdate(
						well_parts_history_Query,
						new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {

								WellPartsEditDTO irmmsWellPartsEditDTO = irmmsWellPartsEditDTOlst
										.get(i);
								ps.setString(1, irmmsWellPartsEditDTO.getWell_id());
								ps.setString(2,
										irmmsWellPartsEditDTO.getWell_name());
								ps.setString(3, irmmsWellPartsEditDTO
										.getProduct_description());
								ps.setString(4, irmmsWellPartsEditDTO.getPart_no());
								ps.setString(5,
										irmmsWellPartsEditDTO.getSerial_no());
								ps.setString(6, "admin");

							}

							@Override
							public int getBatchSize() {
								return irmmsWellPartsEditDTOlst.size();
							}

						});

				for (int trans1 : insert_trans_status) {
					if (trans1 == 0) {
						Success_Fail = "Fail";
						break;
					}
				}
			}
				}
			 }
			}
		return Success_Fail;

	}
	
	
	@Override
	public String editmaintenance(
			final CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst) {

		Map<String, WellRecMaintenanceDTO> wellPartsEditMap = getMaintenanceEditMap(irmmsWellRecMaintenanceDTOlst
				.get(0).getWell_id());
		
for (WellRecMaintenanceDTO irmmsRecMaintenanceEditDTO : irmmsWellRecMaintenanceDTOlst) {
			
			if (!(("").equals(irmmsRecMaintenanceEditDTO.getProduct_description())) 
					&& !(("").equals(irmmsRecMaintenanceEditDTO.getLast_maintenanc_date())) 
							&& !(("").equals(irmmsRecMaintenanceEditDTO.getNext_maintenanc_date()))){
				
				if(StringUtil.parseString(irmmsRecMaintenanceEditDTO.getLast_maintenanc_date()).equalsIgnoreCase(StringUtil.parseString(
					wellPartsEditMap.get(
							irmmsRecMaintenanceEditDTO.getProduct_description())
							.getLast_maintenanc_date()))
					&&  StringUtil.parseString(irmmsRecMaintenanceEditDTO.getNext_maintenanc_date()).equalsIgnoreCase(
							StringUtil.parseString(wellPartsEditMap
									.get(irmmsRecMaintenanceEditDTO
											.getProduct_description())
									.getNext_maintenanc_date()))) {
				logger.info(":: in edit check if ::");
				irmmsWellRecMaintenanceDTOlst.remove(irmmsRecMaintenanceEditDTO);
				}
				
			}
			else
			{
				irmmsWellRecMaintenanceDTOlst.remove(irmmsRecMaintenanceEditDTO);
			}
			
		}

	logger.info("irmmsWellRecMaintenanceDTOlst Final Size ::"
		+ irmmsWellRecMaintenanceDTOlst.size());

	String Success_Fail = "Success";

	String well_maintenance_Query = "update IRMMS_OIL_WELLS_DTL set LAST_MAINTENANCE_DATE=STR_TO_DATE(?,'%d-%m-%Y'),NEXT_MAINTENANCE_DATE=STR_TO_DATE(?,'%d-%m-%Y') where WELL_ID=? and PRODUCT_DESCRIPTION=?";
	
	int[] trans_status = getJdbcTemplate().batchUpdate(
			well_maintenance_Query, new BatchPreparedStatementSetter() {

				@Override
				public void setValues(PreparedStatement ps, int i)
						throws SQLException {

					WellRecMaintenanceDTO irmmsRecMaintenanceEditDTO = irmmsWellRecMaintenanceDTOlst
							.get(i);
					ps.setString(1, irmmsRecMaintenanceEditDTO.getLast_maintenanc_date());
					ps.setString(2, irmmsRecMaintenanceEditDTO.getNext_maintenanc_date());
					ps.setString(3, irmmsRecMaintenanceEditDTO.getWell_id());
					ps.setString(4,
							irmmsRecMaintenanceEditDTO.getProduct_description());
				}

				@Override
				public int getBatchSize() {
					return irmmsWellRecMaintenanceDTOlst.size();
				}

			});
	
	for (int trans : trans_status) {
		if (trans == 0) {
			Success_Fail = "Fail";
			break;
		}
	}
		
		return Success_Fail;
	}

	public Map<String, WellPartsEditDTO> getWellPartsEditMap(String well_id) {
		String edit_parts_query = "select well_id,well_name,part_no,serial_no,product_description from IRMMS_OIL_WELLS_DTL where well_id=?";

		return getJdbcTemplate().query(edit_parts_query,
				new Object[] { well_id },
				new ResultSetExtractor<Map<String, WellPartsEditDTO>>() {

					@Override
					public Map<String, WellPartsEditDTO> extractData(
							ResultSet rs) throws SQLException,
							DataAccessException {

						Map<String, WellPartsEditDTO> wellPartsEditMap = new HashMap<String, WellPartsEditDTO>();
						WellPartsEditDTO irmmsWellPartsEditDTO = null;
						while (rs.next()) {
							irmmsWellPartsEditDTO = new WellPartsEditDTO(rs
									.getString("well_id"), rs
									.getString("well_name"), rs
									.getString("product_description"), rs
									.getString("part_no"), rs
									.getString("serial_no"));
							wellPartsEditMap.put(
									rs.getString("product_description").trim(),
									irmmsWellPartsEditDTO);

						}
						return wellPartsEditMap;
					}

				});
	}
	
	public Map<String, WellRecMaintenanceDTO> getMaintenanceEditMap(String well_id) {
		String edit_parts_query = "select well_id,well_name,date_format(last_maintenance_date,'%d-%m-%Y') last_maintenance_date,date_format(next_maintenance_date,'%d-%m-%Y') next_maintenance_date,product_description from IRMMS_OIL_WELLS_DTL where well_id=?";

		return getJdbcTemplate().query(edit_parts_query,
				new Object[] { well_id },
				new ResultSetExtractor<Map<String, WellRecMaintenanceDTO>>() {

					@Override
					public Map<String, WellRecMaintenanceDTO> extractData(
							ResultSet rs) throws SQLException,
							DataAccessException {

						Map<String, WellRecMaintenanceDTO> wellPartsEditMap = new HashMap<String, WellRecMaintenanceDTO>();
						WellRecMaintenanceDTO irmmsWellRecMaintenanceDTOlst = null;
						while (rs.next()) {
							irmmsWellRecMaintenanceDTOlst = new WellRecMaintenanceDTO(rs
									.getString("well_id"), rs
									.getString("well_name"), rs
									.getString("product_description"), rs
									.getString("last_maintenance_date"), rs
									.getString("next_maintenance_date"));
							wellPartsEditMap.put(
									rs.getString("product_description").trim(),
									irmmsWellRecMaintenanceDTOlst);

						}
						return wellPartsEditMap;
					}

				});
	}

	public List<WellPartsHistoryDTO> getpartshistory(String well_id) {
		String parts_history_query = "select well_id, well_name, product_description, part_no, serial_no, last_updated_by,  date_format(last_updated_date,'%d-%m-%Y')  last_updated_date from irmms_parts_history where well_id=? order by last_updated_date asc";

		List<WellPartsHistoryDTO> parts_history_list = new ArrayList<WellPartsHistoryDTO>();

		try {
			parts_history_list = getJdbcTemplate().query(parts_history_query,
					new Object[] { well_id},
					new WellPartsHistoryMapper());

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(parts_history_query, e.getMessage(), e);
		}

		return parts_history_list;
	}

	public String getwell_name(String well_id) {
		String well_name_query = "select distinct well_name from IRMMS_OIL_WELLS_DTL where well_id=?";
		String well_name = null;

		try {
			well_name = getJdbcTemplate().queryForObject(well_name_query,
					new Object[] { well_id }, String.class);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(well_name_query, e.getMessage(), e);
		}

		return well_name;

	}

	@Override
	public List<RegionDataDTO> getregion() {

		logger.info("getregion  start in DAO ");

		List<RegionDataDTO> irmmsRegion_Datalst = new ArrayList<RegionDataDTO>();

		String region_dataQuery = "select b.group_region, count(*) no_of_wells,Round(AVG(b.latitude),2) latitude,Round(AVG(b.longitude),2) longitude from "
				+ "(select CASE "
				+ "WHEN a.region='A' THEN 'A'"
				+ "WHEN a.region='B' THEN 'B'"
				+ "WHEN a.region='C' THEN 'C'"
				+ "WHEN a.region='D' THEN 'D'"
				+ "WHEN a.region='F' THEN 'F'"
				+ "WHEN a.region='E' or a.region='I' THEN 'E-I' "
				+ "WHEN a.region='G' or a.region='H' THEN 'G-H' "
				+ "END as group_region, latitude, "
				+ "longitude from (select distinct SUBSTRING(WELL_NAME,1,1) region, "
				+ "IFNULL(latitude,0) latitude, IFNULL(longitude,0) longitude from IRMMS_OIL_WELLS_DTL)a)b "
				+ "group by group_region";

		try {
			irmmsRegion_Datalst = getJdbcTemplate().query(region_dataQuery,
					new RegionMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(region_dataQuery, e.getMessage(), e);
		}

		logger.info("getWellData Query --- " + region_dataQuery);
		return irmmsRegion_Datalst;
	}
	
	
	@Override
	public List<WellDTO> getWellbyregion(String region) {
		logger.info("getWellbyregion  start in DAO ");
		
		String[] region_array = region.split("-");
		StringBuffer regionlst = new StringBuffer();		
		for(String region_element : region_array)
		{
			regionlst.append("'").append(region_element).append("',");
		}
		String region_query = regionlst.toString();
		region_query=region_query.substring(0,region_query.length()-1);
		
		logger.info("region query ::"+region_query);

		List<WellDTO> lstWellData = new ArrayList<WellDTO>();

		String welldataQuery = "select Well_Data.WELL_ID as WELL_ID,Well_Data.CUSTOMER_NAME as CUSTOMER_NAME,Well_Data.WELL_NAME as WELL_NAME,Well_Data.FIELD as FIELD,Well_Data.BLOCK_NO as BLOCK_NO,"
				+ "Well_Data.LATITUDE as LATITUDE,Well_Data.LONGITUDE as LONGITUDE,Well_Color.mstatus as mstatus from (select distinct WELL_ID,CUSTOMER_NAME,WELL_NAME,FIELD,"
				+ "BLOCK_NO,LATITUDE,LONGITUDE from IRMMS_OIL_WELLS_DTL) Well_Data, (select WELL_ID, CASE "
				+ "when a.daterange is null then 'GR' "
				+ "WHEN a.daterange <0 THEN 'R' "
				+ "WHEN a.daterange<=30 THEN 'Y' "
				+ "ELSE 'G' END as mstatus from (select WELL_ID,DATEDIFF(STR_TO_DATE(min(irmms_oil_wells_dtl.next_maintenance_date), '%Y-%m-%d'),sysdate())  "
				+ "as daterange from IRMMS_OIL_WELLS_DTL group by WELL_ID)a) Well_Color where Well_Data.WELL_ID =  Well_Color.WELL_ID "
				+ " and SUBSTRING(WELL_NAME,1,1) in ("+region_query+")";

		try {
			lstWellData = getJdbcTemplate().query(welldataQuery,
					new WellMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(welldataQuery, e.getMessage(), e);
		}

		logger.info("getWellbyregion Query --- " + welldataQuery);
		return lstWellData;

	}
	
	@Override
	public List<ExportWellDTO> getexportwell(String wellid) {
	
		logger.info("getExportWell  start in DAO ");
		String wellid_query="";
		if(wellid!=null){
			 wellid_query="where WELL_ID='"+wellid+"'";
		}
		
		List<ExportWellDTO> lstWellData = new ArrayList<ExportWellDTO>();
		
		String welldataQuery = "select WELL_ID,CUSTOMER_NAME,WELL_NAME,FIELD,BLOCK_NO,LATITUDE,LONGITUDE,PART_NO,SERIAL_NO," +
				"PRODUCT_DESCRIPTION,PMS_DESCRIPTION," +
				"date_format(last_maintenance_date,'%d-%m-%Y')  LAST_MAINTENANCE_DATE," +
				"date_format(NEXT_MAINTENANCE_DATE,'%d-%m-%Y')  NEXT_MAINTENANCE_DATE," +
				"mstatus from IRMMS_OIL_WELLS_DTL Well_Data, " +
				"(select WELL_ID well, CASE when a.daterange is null then 'B' WHEN a.daterange <0 THEN 'ZR' "+
				"WHEN a.daterange<=30 THEN 'Y' ELSE 'G' END as mstatus from " +
				"(select WELL_ID ,DATEDIFF(STR_TO_DATE(min(irmms_oil_wells_dtl.next_maintenance_date), '%Y-%m-%d'),sysdate()) "+
				"as daterange from IRMMS_OIL_WELLS_DTL "+wellid_query+" group by WELL_ID)a) Well_Color "+
				"where Well_Data.WELL_ID=Well_Color.well order by mstatus desc,WELL_NAME";
		
		try {
			lstWellData = getJdbcTemplate().query(welldataQuery,
					new ExportWellMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(welldataQuery, e.getMessage(), e);
		}

		logger.info("getExportWell Query --- " + welldataQuery);
		return lstWellData;		
	}

	@Override
	public List<CustomerDTO> getCustomer() {
		logger.info("getCustomer  start in DAO ");

		List<CustomerDTO> customerLst = new ArrayList<CustomerDTO>();

		String customerDataQuery = "SELECT DISTINCT CUSTOMER_NAME FROM IRMMS_OIL_WELLS_DTL";

		try {
			customerLst = getJdbcTemplate().query(customerDataQuery, new CustomerMapper());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(customerDataQuery, e.getMessage(), e);
		}

		logger.info("Customer Data Query --- " + customerDataQuery);
		return customerLst;
	}

	
	@Override
	public List<WellDTO> searchWellData(WellPartsSearchDTO wellPartsSearchDTO) {
		List<WellDTO> lstWellData = new ArrayList<WellDTO>();
		WellMapper mapperWell =  new WellMapper();

		String procSearchData = "call IRMMS_WELLDATA_SEARCH(?,?,?,?,?,?)";
		Connection con = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String mstatus = "";
		
		if(!"".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
		{
			if("overdue".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
			{
				mstatus = "OVERDUE";
			}
			else if("less1".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
			{
				mstatus = "LESS1";
			}
			else if("grtr1".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
			{
				mstatus = "GRTR1";
			}
			else if("dtNA".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
			{
				mstatus = "DTNA";
			}
			else if("0".equalsIgnoreCase(wellPartsSearchDTO.getMaintenance_date()))
			{
				mstatus = "";
			}
			else
			{
				mstatus = "GR";
			}
			
		}
		
		try{
			
			con = getJdbcTemplate().getDataSource().getConnection();
			cstmt = con.prepareCall(procSearchData);
			
			cstmt.setString(1, wellPartsSearchDTO.getCustomer_name());
			cstmt.setString(2, wellPartsSearchDTO.getWell_name());
			cstmt.setString(3, wellPartsSearchDTO.getPart_no());
			cstmt.setString(4, wellPartsSearchDTO.getSerial_no());
			cstmt.setString(5, wellPartsSearchDTO.getFieldName());
			cstmt.setString(6, mstatus);
			
			
			cstmt.execute();
			
			rs =  cstmt.getResultSet();
			
			while(rs.next())
			{
				WellDTO wellDTO = mapperWell.mapRow(rs, 0);
				lstWellData.add(wellDTO);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getLocalizedMessage(), e);
			throw new MVTException(procSearchData, e.getMessage(), e);
		}
		finally{
			try {
				if(con!=null){
					con.close();
				}
				if(cstmt!=null){
					cstmt.close();
				}
				if(rs!=null){
					rs.close();
				}
				
			} catch (SQLException e) {
				logger.error(e.getLocalizedMessage(), e);
				throw new MVTException("Failed closing the connection : ", e.getMessage(), e);
			}
		}
		logger.info("Final Query for Testing ------------> "+procSearchData);

		logger.info("getWellData Query --- " + lstWellData);
		return lstWellData;	
	}
	
	
	 @Override
		public List<CustomerNameDTO> searchCustomerData(WellPartsSearchDTO irmmsWellPartsSearchDTO) {
			List<CustomerNameDTO> lstWellData = new ArrayList<CustomerNameDTO>();
			WellCustMapper mapperWell =  new WellCustMapper();
	
			String procSearchData = "call IRMMS_CUSTOMERDATA_SEARCH(?,?,?,?,?,?)";
			Connection con = null;
			CallableStatement cstmt = null;
			ResultSet rs = null;
			String mstatus = "";
			
			if(!"".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
			{
				if("overdue".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
				{
					mstatus = "OVERDUE";
				}
				else if("less1".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
				{
					mstatus = "LESS1";
				}
				else if("grtr1".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
				{
					mstatus = "GRTR1";
				}
				else if("dtNA".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
				{
					mstatus = "DTNA";
				}
				else if("0".equalsIgnoreCase(irmmsWellPartsSearchDTO.getMaintenance_date()))
				{
					mstatus = "";
				}
				else
				{
					mstatus = "GR";
				}
			}
			
			try{
				
				con = getJdbcTemplate().getDataSource().getConnection();
				cstmt = con.prepareCall(procSearchData);
				
				cstmt.setString(1, irmmsWellPartsSearchDTO.getCustomer_name());
				cstmt.setString(2, irmmsWellPartsSearchDTO.getWell_name());
				cstmt.setString(3, irmmsWellPartsSearchDTO.getPart_no());
				cstmt.setString(4, irmmsWellPartsSearchDTO.getSerial_no());
				cstmt.setString(5, irmmsWellPartsSearchDTO.getFieldName());
				cstmt.setString(6, mstatus);
				
				cstmt.execute();
				
				rs =  cstmt.getResultSet();
				
				while(rs.next())
				{
					CustomerNameDTO wellDTO = mapperWell.mapRow(rs, 0);
					lstWellData.add(wellDTO);
				}
			}
			catch(Exception e)
			{
				logger.error(e.getLocalizedMessage(), e);
				throw new MVTException(procSearchData, e.getMessage(), e);
			}
			finally{
				try {
					if(con!=null){
						con.close();
					}
					if(cstmt!=null){
						cstmt.close();
					}
					if(rs!=null){
						rs.close();
					}
					
				} catch (SQLException e) {
					logger.error(e.getLocalizedMessage(), e);
					throw new MVTException("Failed closing the connection : ", e.getMessage(), e);
				}
			}
			
			logger.info("searchCustomerData Query --- " + lstWellData);
			return lstWellData;	
		}

	 @Override
		public String uploadDataImport(UploadDTO wellDtl) {
			String status = "Success";
			String updateStatus = "Success";
			String insertPartStatus = "Success";
			
			final List<UploadDTO> lstData = new ArrayList<UploadDTO>();
			lstData.add(wellDtl);
			
			String updateQuery = "update IRMMS_OIL_WELLS_DTL set PART_NO=?,SERIAL_NO=?, PRODUCT_DESCRIPTION=?, LAST_MAINTENANCE_DATE=str_to_date(?,'%d-%M-%y'), NEXT_MAINTENANCE_DATE=str_to_date(?,'%d-%M-%y') where WELL_NAME=? and PMS_DESCRIPTION=?";
			String insertPartQuery = "insert into IRMMS_PARTS_HISTORY(WELL_NAME,PRODUCT_DESCRIPTION,PART_NO,SERIAL_NO,PMS_DESCRIPTION,LAST_UPDATED_BY,LAST_UPDATED_DATE)  values(?,?,?,?,?,?,sysdate())";
			
			//logger.info(":: in Batch Update if ::"+updateQuery);
			for(UploadDTO wellData : lstData)
			{
				logger.info("wellData.getPart_no() "+wellData.getPART_NO());
				logger.info("wellData.getSerial_no() "+wellData.getSERIAL_NO());
				logger.info("wellData.getProduct_desc() "+wellData.getPRODUCT_DESCRIPTION());
				logger.info("wellData.getLast_maintenance_date() "+wellData.getLAST_MAINTENANCE_DATE());
				logger.info("wellData.getNext_maintenance_date() "+wellData.getNEXT_MAINTENANCE_DATE());
				logger.info("wellData.getWell_name() "+wellData.getWELL_NAME());
				logger.info("wellData.getPms_desc() "+wellData.getPMS_DESCRIPTION().toUpperCase());
			}
			int[] trans_status = getJdbcTemplate().batchUpdate(
					updateQuery, new BatchPreparedStatementSetter() {

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							UploadDTO wellData = lstData.get(i);
							ps.setString(1, wellData.getPART_NO());
							ps.setString(2, wellData.getSERIAL_NO());
							ps.setString(3, wellData.getPRODUCT_DESCRIPTION());
							ps.setString(4, wellData.getLAST_MAINTENANCE_DATE());
							ps.setString(5, wellData.getNEXT_MAINTENANCE_DATE());
							ps.setString(6, wellData.getWELL_NAME());
							ps.setString(7, wellData.getPMS_DESCRIPTION().toUpperCase());
							
						}

						@Override
						public int getBatchSize() {
							return lstData.size();
						}

					});
			logger.info("TRANS ----------- "+trans_status);
			for (int trans : trans_status) {
				logger.info("UPDATE TRANS ---- "+trans);
				if (trans == 0) {
					updateStatus = "Fail";
					break;
				}
			}
			logger.info("UPDATE STATUS:::::::::::"+updateStatus);
			if(!"Fail".equalsIgnoreCase(updateStatus))
			{
				int[] insert_part_status = getJdbcTemplate().batchUpdate(
						insertPartQuery, new BatchPreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								UploadDTO wellData = lstData.get(i);
								ps.setString(1, wellData.getWELL_NAME());
								ps.setString(2, wellData.getPRODUCT_DESCRIPTION());
								ps.setString(3, wellData.getPART_NO());
								ps.setString(4, wellData.getSERIAL_NO());
								ps.setString(5, wellData.getPMS_DESCRIPTION().toUpperCase());
								ps.setString(6,"admin");
							}

							@Override
							public int getBatchSize() {
								return lstData.size();
							}

						});
				logger.info("Part TRANS ----------- "+insert_part_status);
				for (int trans : insert_part_status) {
					logger.info("INSERT TRANS ---- "+trans);
					if (trans == 0) {
						insertPartStatus = "Fail";
						break;
					}
				}
			}
			
			logger.info("updateStatus : "+updateStatus);
			logger.info("insertPartStatus : "+insertPartStatus);
			
			if("Fail".equalsIgnoreCase(updateStatus))
			{
				status = "Fail";
			}
			logger.info("FINAL STATUS ----------- "+status);
			return status;
		}

	 public List<OverdueDTO> getoverdue(){
		 
		 logger.info("getoverdue  start in DAO ");

			List<OverdueDTO> lstOverdueData = new ArrayList<OverdueDTO>();

			String overduedataQuery = "select * from overdueview";
					
			try {
				lstOverdueData = getJdbcTemplate().query(overduedataQuery,
						new OverdueMapper());
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage(), e);
				throw new MVTException(overduedataQuery, e.getMessage(), e);
			}

			logger.info("getoverdue Query --- " + overduedataQuery);
			return lstOverdueData;
	 }
	 }
