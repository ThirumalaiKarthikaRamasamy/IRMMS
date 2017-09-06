package com.irmms.rest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.quartz.JobExecutionException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.irmms.dto.OverdueDTO;
import com.irmms.dto.WellRecMaintenanceDTO;
import com.irmms.dto.WellSearchData;
import com.irmms.dto.CustomerDTO;
import com.irmms.dto.RecMaintenanceDTO;
import com.irmms.dto.RecMaintenanceSparesDTO;
import com.irmms.dto.RegionDataDTO;
import com.irmms.dto.WellDTO;
import com.irmms.dto.WellPartsDTO;
import com.irmms.dto.WellPartsEditDTO;
import com.irmms.dto.Well_Data;
import com.irmms.dto.WellPartsHistoryDTO;
import com.irmms.logger.MVTLogger;
import com.irmms.services.EmailAlertController;
import com.irmms.services.MVTServiceIntf;
import com.irmms.util.SpringApplicationContext;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/mvtapp")
public class MVTRestServices {

	/** The logger. */
	private MVTLogger logger = MVTLogger.getLogger(this.getClass());

	/**
	 * This method returns the state level count
	 */

	@GET
	@Path("getWellData")
	@Produces(MediaType.APPLICATION_XML)
	public List<WellDTO> getWellData(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext) {
		List<WellDTO> lstAISReadData = getWellData();
		logger.info("getWell size = " + lstAISReadData.size());

		return lstAISReadData;
	}

	private List<WellDTO> getWellData() {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<WellDTO> lstWell = new ArrayList<WellDTO>();

		lstWell = mvtService.getWellData();

		return lstWell;
	}

	@GET
	@Path("getWellPartsData")
	@Produces(MediaType.APPLICATION_XML)
	public Well_Data getWellPartsData(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,
			@QueryParam("well_id") String well_id) {
		List<WellPartsDTO> lstAISReadData = getWellPartsData(well_id);
		logger.info("getWell Parts size = " + lstAISReadData.size());
		
		logger.info("getWell Parts End ");

		Collections.sort(lstAISReadData);

		Well_Data irmmsWell_Data = new Well_Data();

		
		irmmsWell_Data
				.setRec_maintenance_spares(getRec_MaintenanceSpares(getwell_name(well_id)));
		logger.info("getWell Rec_maintenance_spares End ");
		
		List<WellPartsHistoryDTO> parts_history_list = getparts_history(well_id);
		
		List<WellPartsHistoryDTO> perwell_parts_history_list = null;
		
		for(WellPartsDTO irmmsWellPartsDTO:lstAISReadData)
		{
			perwell_parts_history_list = new ArrayList<WellPartsHistoryDTO>();
			
			for(WellPartsHistoryDTO irmmsWell_Parts_HistoryDTO : parts_history_list)
			{
				
				if(irmmsWell_Parts_HistoryDTO.getProduct_description().equalsIgnoreCase(irmmsWellPartsDTO.getProduct_description()))
				{
					perwell_parts_history_list.add(irmmsWell_Parts_HistoryDTO);
				}
			}
			
			irmmsWellPartsDTO.setPerwell_parts_history_list(perwell_parts_history_list);
		}
		
		logger.info("getWell Parts_history End ");
		
		irmmsWell_Data.setWell_parts_data(lstAISReadData);

		return irmmsWell_Data;
	}

	private List<WellPartsDTO> getWellPartsData(String well_id) {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<WellPartsDTO> lstWell = new ArrayList<WellPartsDTO>();

		lstWell = mvtService.getWellPartsData(well_id);

		return lstWell;
	}

	public String getwell_name(String well_id) {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		return mvtService.getwell_name(well_id);
	}

	@GET
	@Path("getRecMaintenance")
	@Produces(MediaType.APPLICATION_XML)
	public List<RecMaintenanceDTO> getRec_Maintenance(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext) {
		List<RecMaintenanceDTO> lstAISReadData = getRec_MaintenanceData();
		logger.info("getRec_MaintenanceData size = " + lstAISReadData.size());

		return lstAISReadData;
	}

	private List<RecMaintenanceDTO> getRec_MaintenanceData() {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<RecMaintenanceDTO> lstRec_Maintenance = new ArrayList<RecMaintenanceDTO>();

		lstRec_Maintenance = mvtService.getRec_Maintenance();

		return lstRec_Maintenance;
	}

	@GET
	@Path("getRecMaintenanceSpears")
	@Produces(MediaType.APPLICATION_XML)
	public List<RecMaintenanceSparesDTO> getRec_Maintenance_spears(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,
			@QueryParam("well_name") String well_name) {
		logger.info("getRec_Maintenance spears Well Name = " + well_name);

		List<RecMaintenanceSparesDTO> lstAISReadData = getRec_MaintenanceSpares(well_name);
		logger.info("getRec_Maintenance spears size = " + lstAISReadData.size());

		return lstAISReadData;
	}

	private List<RecMaintenanceSparesDTO> getRec_MaintenanceSpares(
			String well_name) {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<RecMaintenanceSparesDTO> lstRec_Maintenance_spears = new ArrayList<RecMaintenanceSparesDTO>();

		lstRec_Maintenance_spears = mvtService
				.getRec_MaintenanceSpares(well_name);

		return lstRec_Maintenance_spears;

	}

	@POST
	@Path("editwellparts")
	@Consumes("application/xml")
	@Produces(MediaType.APPLICATION_XML)
	public List<WellPartsDTO> comsumeXML(
			CopyOnWriteArrayList<WellPartsEditDTO> irmmsWellPartsEditDTOlst) {

		List<WellPartsDTO> lstAISReadData = null;

		try {
			if (irmmsWellPartsEditDTOlst.size() > 0) {
				String well_id = irmmsWellPartsEditDTOlst.get(0).getWell_id();
				
				String Success_Fail = editwellparts(irmmsWellPartsEditDTOlst);

				if (Success_Fail.equalsIgnoreCase("Success")) {
					lstAISReadData = getWellPartsData(well_id);
					logger.info("getWell Parts size = " + lstAISReadData.size());
					Collections.sort(lstAISReadData);
				}
			}
		} catch (Exception ex) {
			return lstAISReadData;
		}

		return lstAISReadData;

	}

	private String editwellparts(
			CopyOnWriteArrayList<WellPartsEditDTO> irmmsWellPartsEditDTOlst) {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		String Success_Fail = mvtService.editwellparts(irmmsWellPartsEditDTOlst);

		return Success_Fail;
	}
	
	@POST
	@Path("editmaintenance")
	@Consumes("application/xml")
	@Produces(MediaType.APPLICATION_XML)
	public List<WellPartsDTO> comsumeRecXML(
			CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst) {
		
		List<WellPartsDTO> lstAISReadData = null;
		try {
			if (irmmsWellRecMaintenanceDTOlst.size() > 0) {
				String well_id = irmmsWellRecMaintenanceDTOlst.get(0).getWell_id();
				String Success_Fail = editmaintenance(irmmsWellRecMaintenanceDTOlst);
				
				if (Success_Fail.equalsIgnoreCase("Success")) {
					lstAISReadData = getWellPartsData(well_id);
					logger.info("getWell Parts size = " + lstAISReadData.size());
					Collections.sort(lstAISReadData);
				}
				}
			
		}
	 catch (Exception ex) {
		return lstAISReadData;
	}

	return lstAISReadData;
	}
	
	private String editmaintenance(
			CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst) {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		String Success_Fail = mvtService.editmaintenance(irmmsWellRecMaintenanceDTOlst);

		return Success_Fail;
	}

	@GET
	@Path("getPartsHistory")
	@Produces(MediaType.APPLICATION_XML)
	public List<WellPartsHistoryDTO> getpartshistory(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,
			@QueryParam("well_id") String well_id) {
		logger.info("get Parts History well_id =" + well_id);

		List<WellPartsHistoryDTO> parts_history_list = getparts_history(well_id);

		logger.info("Parts History size = " + parts_history_list.size());

		return parts_history_list;
	}

	private List<WellPartsHistoryDTO> getparts_history(String well_id) {
		logger.info("get Parts History well_id =" + well_id);
		
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<WellPartsHistoryDTO> parts_history_list = mvtService
				.getpartshistory(well_id);
		logger.info("Parts History size = " + parts_history_list.size());

		return parts_history_list;
	}
	
	
	@GET
	@Path("getregion")
	@Produces(MediaType.APPLICATION_XML)
	public 	List<RegionDataDTO> getregion(
			@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext) {
		
		List<RegionDataDTO> irmmsRegion_Datalst = get_region();

		logger.info("IRMMSRegion Data size = " + irmmsRegion_Datalst.size());
		
		Collections.sort(irmmsRegion_Datalst);

		return irmmsRegion_Datalst;
	}

	private List<RegionDataDTO> get_region() {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<RegionDataDTO> irmmsRegion_Datalst = mvtService.getregion();
		
		return irmmsRegion_Datalst;
	}
	
	
	@GET
	@Path("getWellbyregion")
	@Produces(MediaType.APPLICATION_XML)
	public List<WellDTO> getWellbyregion(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,
			@QueryParam("region") String region) {
		List<WellDTO> lstAISReadData = getWellData(region);
		logger.info("getWell size = " + lstAISReadData.size());

		return lstAISReadData;
	}

	private List<WellDTO> getWellData(String region) {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<WellDTO> lstWell = new ArrayList<WellDTO>();

		lstWell = mvtService.getWellbyregion(region);

		return lstWell;
	}
	
	
	
	@GET
	@Path("getexportwell")
	@Consumes(MediaType.TEXT_PLAIN) 
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	//@Produces("application/vnd.ms-excel")
	public Response getexportwell(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,
			@QueryParam("wellid") String wellid) {
				
		Response res = getexport_well( wellid);
		logger.info("get export Well size = " + res);
		return res;
	}

	private Response getexport_well(String wellid) {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		Response res = mvtService.getexportwell(wellid);
		return res;
	}
	
	
	@GET
	@Path("getCustomer")
	@Produces(MediaType.APPLICATION_XML)
	public 	List<CustomerDTO> getCustomer(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext)
	{
		List<CustomerDTO> customerData = getCustData();
		logger.info("get customer = " + customerData.size());

		return customerData;
	}

	private List<CustomerDTO> getCustData() {
		
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		List<CustomerDTO> lstCust = new ArrayList<CustomerDTO>();

		lstCust = mvtService.getCustomer();

		return lstCust;
	}
	
	@GET
	@Path("downloadTemplate")
	@Consumes(MediaType.TEXT_PLAIN) 
	@Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	//@Produces("application/vnd.ms-excel")
	public void downloadTemplate(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext
			) {
				
        String filePath = "C:\\Users\\tr809419\\Downloads\\IRMMS Import Template.xlsm"; 

try{
        File downloadFile = new File(filePath);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // set content attributes for the response
        response.setContentType("application/octet-stream");
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                     downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[1000];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
               outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
}
catch(Exception ex)
{
	System.out.println(ex);
}

	}
	

	@POST
	@Path("searchWellData")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_XML)
	public WellSearchData searchWellData(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext,String data)
	{
		WellSearchData wellData = searchWellData(data);
		logger.info("get search data ");

		return wellData;
	}

	private WellSearchData searchWellData(String data) {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");

		WellSearchData wellData = new WellSearchData();

		wellData = mvtService.getSearchData(data);

		return wellData;
	}
	
	@POST
	@Path("uploadFile")
	@Consumes("multipart/form-data")
	  public void uploadFileHandler(@Context HttpServletRequest request,
				@Context HttpServletResponse response,MultipartFormDataInput input) {
		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");
		String message ="";
		java.net.URI location =null;
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("uploadedFile");
		for (InputPart inputPart : inputParts) {

		 try {

			//convert the uploaded file to inputstream
			InputStream inputStream = inputPart.getBody(InputStream.class,null);
			message = mvtService.uploadWellData(inputStream);
			if(message == "Success")
			{
				message = "File Uploaded Successfully";
			}
			else if(message == "Fail")
			{
				message="File is not Uploaded Successfully.";
			}
			response.setContentType("text/plain");
			 PrintWriter out = response.getWriter();
			 out.print(message);
			 out.flush();
		  } catch (Exception e) {
			e.printStackTrace();
		  }
		}
		
		
		}
	
	@GET
	@Path("getOverdue")
	@Produces(MediaType.APPLICATION_XML)
	public List<OverdueDTO> getOverdue(@Context HttpServletRequest request,
			@Context HttpServletResponse response,
			@Context ServletContext servletContext) {
		List<OverdueDTO> lstAISReadData = getOverdue();
		logger.info("getWell size = " + lstAISReadData.size());
String name = "karthi";
HttpSession session = request.getSession();
session.setAttribute("name", name);
		return lstAISReadData;
	}

	private List<OverdueDTO> getOverdue() {

		MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
				.getBean("mvtService");
		
		List<OverdueDTO> lstWell = new ArrayList<OverdueDTO>();

		lstWell = mvtService.getoverdue();

		return lstWell;
	}
	
}
