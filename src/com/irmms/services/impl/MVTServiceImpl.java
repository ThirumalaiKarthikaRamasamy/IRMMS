package com.irmms.services.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.irmms.dao.MVTDAOIntf;
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
import com.irmms.dto.WellSearchData;
import com.irmms.services.MVTServiceIntf;

@Service("mvtService")
@Repository
public class MVTServiceImpl implements MVTServiceIntf {

	
	@Autowired
	@Qualifier("mvtDAO")
	private MVTDAOIntf mvtDAO;

	public MVTDAOIntf getMvtDAO() {
		return mvtDAO;
	}

	public void setMvtDAO(MVTDAOIntf mvtDAO) {
		this.mvtDAO = mvtDAO;
	}
	
	
	@Override
	public List<WellDTO> getWellData() {
		// TODO Auto-generated method stub
		return mvtDAO.getWellData();
	}
	
	
	@Override
	public List<WellPartsDTO> getWellPartsData(String well_id) {
		// TODO Auto-generated method stub
		return mvtDAO.getWellPartsData(well_id);
	}
	
	@Override
	public List<RecMaintenanceDTO> getRec_Maintenance()
	{
		return mvtDAO.getRec_Maintenance();
	}
	
	@Override
	public List<RecMaintenanceSparesDTO> getRec_MaintenanceSpares(String well_name)
	{
		return mvtDAO.getRec_MaintenanceSpares(well_name);
	}
	
	@Override
	public String editwellparts( CopyOnWriteArrayList<WellPartsEditDTO> irmmsWellPartsEditDTOlst)
	{
		return mvtDAO.editwellparts(irmmsWellPartsEditDTOlst);
	}
	
	@Override
	public String editmaintenance(CopyOnWriteArrayList<WellRecMaintenanceDTO> irmmsWellRecMaintenanceDTOlst)
	{
		return mvtDAO.editmaintenance(irmmsWellRecMaintenanceDTOlst);
	}
	
	@Override
	public List<WellPartsHistoryDTO> getpartshistory(String well_id)
	{
		return mvtDAO.getpartshistory(well_id);
	}

	@Override
	public String getwell_name(String well_id) {
		
		return mvtDAO.getwell_name(well_id);
	}
	
	@Override
	public List<RegionDataDTO> getregion()
	{
		return mvtDAO.getregion();
	}
	
	@Override
	public List<WellDTO> getWellbyregion(String region)
	{
		return mvtDAO.getWellbyregion(region);
	}
	
	@Override
	public Response getexportwell(String wellid){
		
		List<ExportWellDTO> exportWells= mvtDAO.getexportwell(wellid);
				
		File file = null;
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Well Details");
        XSSFRow row = null;
        XSSFCell cell = null;
        
        XSSFCellStyle headerStyle= workbook.createCellStyle();
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headerStyle.setBorderRight(CellStyle.BORDER_THIN);
        headerStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        
        XSSFFont headerFont= workbook.createFont();
        headerFont.setFontHeightInPoints((short)10);
        headerFont.setFontName("GE Inspira");
        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setItalic(false);
        
        XSSFCellStyle dataStyle=null;
        
        XSSFCellStyle blackStyle= workbook.createCellStyle();
        blackStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        blackStyle.setBorderBottom(CellStyle.BORDER_THIN);
        blackStyle.setBorderLeft(CellStyle.BORDER_THIN);
        blackStyle.setBorderRight(CellStyle.BORDER_THIN);
        blackStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        blackStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        blackStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        blackStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        blackStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        XSSFFont dataFont= workbook.createFont();
        dataFont.setFontHeightInPoints((short)10);
        dataFont.setFontName("GE Inspira");
        dataFont.setItalic(false);
        
        XSSFCellStyle greenColorStyle = workbook.createCellStyle();
        greenColorStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        greenColorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        greenColorStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        greenColorStyle.setBorderBottom(CellStyle.BORDER_THIN);
        greenColorStyle.setBorderLeft(CellStyle.BORDER_THIN);
        greenColorStyle.setBorderRight(CellStyle.BORDER_THIN);
        greenColorStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        greenColorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        greenColorStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        greenColorStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        greenColorStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
       
        XSSFCellStyle redColorStyle = workbook.createCellStyle();
        redColorStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        redColorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        redColorStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        redColorStyle.setBorderBottom(CellStyle.BORDER_THIN);
        redColorStyle.setBorderLeft(CellStyle.BORDER_THIN);
        redColorStyle.setBorderRight(CellStyle.BORDER_THIN);
        redColorStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        redColorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        redColorStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        redColorStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        redColorStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        XSSFCellStyle yellowColorStyle = workbook.createCellStyle();
        yellowColorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        yellowColorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        yellowColorStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        yellowColorStyle.setBorderBottom(CellStyle.BORDER_THIN);
        yellowColorStyle.setBorderLeft(CellStyle.BORDER_THIN);
        yellowColorStyle.setBorderRight(CellStyle.BORDER_THIN);
        yellowColorStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        yellowColorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        yellowColorStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        yellowColorStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        yellowColorStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        XSSFCellStyle greyColorStyle = workbook.createCellStyle();
        greyColorStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        greyColorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        greyColorStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        
        greyColorStyle.setBorderBottom(CellStyle.BORDER_THIN);
        greyColorStyle.setBorderLeft(CellStyle.BORDER_THIN);
        greyColorStyle.setBorderRight(CellStyle.BORDER_THIN);
        greyColorStyle.setBorderTop(CellStyle.BORDER_THIN);
        
        greyColorStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        greyColorStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        greyColorStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        greyColorStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        
        row = sheet.createRow(0);
        cell = row.createCell(4);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Maintenance Status");
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(0,0,4,6));
        
        row = sheet.createRow(2);
        cell = row.createCell(4);
        cell.setCellStyle(redColorStyle);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Overdue");
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(2,2,5,6));
        
        row = sheet.createRow(3);
        cell = row.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(3,3,5,6));
        cell.setCellStyle(yellowColorStyle);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("< One month");
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        
        row = sheet.createRow(4);
        cell = row.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(4,4,5,6));
        cell.setCellStyle(greenColorStyle);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("> One month");
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        
        row = sheet.createRow(5);
        cell = row.createCell(4);
        sheet.addMergedRegion(new CellRangeAddress(5,5,5,6));
        cell.setCellStyle(greyColorStyle);
        cell = row.createCell(5);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Date N/A");
        cell = row.createCell(6);
        cell.setCellStyle(headerStyle);
        
        
        row = sheet.createRow(7);
        int cellNo=0;
        List<String> headerList = new ArrayList<String>();              

	        headerList.add("WELL ID");
	        headerList.add("CUSTOMER NAME");
	        headerList.add("WELL NAME");
	        headerList.add("FIELD");
	        headerList.add("BLOCK NO");
	        headerList.add("LATITUDE");
	        headerList.add("LONGITUDE");
	        headerList.add("PART NO");
	        headerList.add("SERIAL NO");
	        headerList.add("PRODUCT DESCRIPTION");
	        headerList.add("PMS DESCRIPTION");	        
	        headerList.add("LAST MAINTENANCE DATE");
	        headerList.add("NEXT MAINTENANCE DATE");
	        headerList.add("QUANTITY");
	        headerList.add("PRICE");
	        headerList.add("SALES ORDER TICKET NO");
	        headerList.add("M STATUS");
	        

        for (String header : headerList) {
            cell = row.createCell(cellNo);
            headerStyle.setFont(headerFont);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
            cellNo++;
        }
        for(int columnPosition = 0; columnPosition< 17; columnPosition++) {
            sheet.autoSizeColumn((short) (columnPosition));
       }        
        int rowNo = 8;
        String wellname ="";
        for(int i=0;i<exportWells.size();i++)
        {
      
        	ExportWellDTO wellDTO = exportWells.get(i);
        	if(wellDTO.getMstatus().equals("G"))
        	{
        		dataStyle = greenColorStyle;
        	}
        	else if(wellDTO.getMstatus().equals("ZR"))
        	{
        		dataStyle = redColorStyle;
        	}
        	else if(wellDTO.getMstatus().equals("Y"))
        	{
        		dataStyle = yellowColorStyle;
        	}
        	else if(wellDTO.getMstatus().equals("B"))
        	{
        		dataStyle = greyColorStyle;
        	}
        	else{
        		dataStyle = blackStyle;
        	}
        	wellname=wellDTO.getWELL_NAME();
            row = sheet.createRow(rowNo++);
            dataStyle.setFont(dataFont);
            blackStyle.setFont(dataFont);
            cellNo = 0;
            dataStyle.setFont(dataFont);
            blackStyle.setFont(dataFont);
            
            XSSFCell dataCell;
            
            
            dataCell = row.createCell(cellNo++);            
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getWELL_ID());                       
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getCUSTOMER_NAME());

            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(dataStyle);
            dataCell.setCellValue(wellDTO.getWELL_NAME());

            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getFIELD());

            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getBLOCK_NO());

            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getLATITUDE());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getLONGITUDE());
            
            //state
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getPART_NO());
            //county
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getSERIAL_NO());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getPRODUCT_DESCRIPTION());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getPMS_DESCRIPTION());
           
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getLAST_MAINTENANCE_DATE());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getNEXT_MAINTENANCE_DATE());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getQUANTITY());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getPRICE());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getSALES_ORDER_TICKET_NO());
            
            dataCell = row.createCell(cellNo++);
            dataCell.setCellStyle(blackStyle);
            dataCell.setCellValue(wellDTO.getMstatus());
            
        }
        
                

        for(int sheetNum=0;sheetNum<workbook.getNumberOfSheets();sheetNum++)
        {
	        XSSFRow wellRow = workbook.getSheetAt(sheetNum).getRow(0);
	        for(int colNum = 0; colNum<wellRow.getLastCellNum();colNum++)   
	        {
	            workbook.getSheetAt(sheetNum).autoSizeColumn(colNum);
	        }
        }
        
        try {
        	
        String fileName	= "Data.xls";
        Date date1 = new Date();
	    String DATE_FORMAT = "MM-dd-yyyy";
	    SimpleDateFormat sdf1 = new SimpleDateFormat(DATE_FORMAT);
	    String currentDate = sdf1.format(date1);

	    if(wellid!=null)
	    {	    
        	fileName = "IRMMS"+" "+wellname+" "+currentDate+".xlsx";
	    }
	    else{
	    	//fileName = company+" "+currentDate+".xlsx";
        	fileName = "IRMMS"+" "+currentDate+".xlsx";
	    }

            file= new File(fileName);
            FileOutputStream fos = new FileOutputStream(file); 
            workbook.write(fos);
            fos.close();
            System.out.println("In service after DAO");
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		}
		
		String contentType = "application/vnd.ms-excel";
        ResponseBuilder response = Response.ok((Object) file, contentType);
        response.header("Content-Disposition", "inline; filename="+ file.getName());

        return response.build();
	}

	@Override
	public List<CustomerDTO> getCustomer() {

		return mvtDAO.getCustomer();
	}


	@Override
	public WellSearchData getSearchData(String data) {

		WellSearchData search_Data = new WellSearchData();
		
		WellPartsSearchDTO irmmsWellPartsSearchDTO = new WellPartsSearchDTO();
		
		String keyRegion = "";
		try
		{
			System.out.println("Input String"+data);
			JSONParser parse = new JSONParser();
			JSONObject jObj1 = (JSONObject)parse.parse(data);
			System.out.println("jObj1 : "+jObj1);
			System.out.println(jObj1.get("searchData"));
			JSONArray jArr1 = (JSONArray)jObj1.get("searchData");
			System.out.println("jArr1 : "+jArr1);
			System.out.println("jArr1 : "+jArr1.get(0));
			JSONObject jObj2 = (JSONObject)jArr1.get(0);
			System.out.println("jObj2 : "+jObj2); 
			//JSONArray jArr = (JSONArray) parse.parse(data);
			String custName = "";
		    String well_name="";
		    String part_no="";
		    String serial_no="";
		    String maintenanceDt = "";
		    String product_description = "";
		    String criteria_status = "";
		    String fieldName = "";
		    
		    
			for(int i=0;i<jArr1.size();i++)
			{
				JSONObject jObj = (JSONObject) jArr1.get(i);
				System.out.println("data in for : "+jObj);
				//JSONArray jObj  = (JSONArray) jObjVal.get("searchData");
				
				if (jObj.get("custName") != null) {
					custName = jObj.get("custName").toString();
					irmmsWellPartsSearchDTO.setCustomer_name(custName);
				}
				else
				{
					irmmsWellPartsSearchDTO.setCustomer_name(custName);
				}
				//System.out.println("well_name before if "+jObj.get("well_name").toString());
				if (jObj.get("well_name") != null) {
					System.out.println("well_name in if : "+jObj.get("well_name").toString());
					well_name = jObj.get("well_name").toString();
					irmmsWellPartsSearchDTO.setWell_name(well_name);
				}
				else
				{
					irmmsWellPartsSearchDTO.setWell_name(well_name);
				}
				
				if (jObj.get("part_number") != null) {
					part_no = jObj.get("part_number").toString();
					irmmsWellPartsSearchDTO.setPart_no(part_no);
				}
				else
				{
					irmmsWellPartsSearchDTO.setPart_no(part_no);
				}
				
				if (jObj.get("serial_number") != null) {
					serial_no = jObj.get("serial_number").toString();
					irmmsWellPartsSearchDTO.setSerial_no(serial_no);
				}
				else
				{
					irmmsWellPartsSearchDTO.setSerial_no(serial_no);
				}
				
				if (jObj.get("maintenanceDt") != null) {
					maintenanceDt = jObj.get("maintenanceDt").toString();
					irmmsWellPartsSearchDTO.setMaintenance_date(maintenanceDt);
				}
				else
				{
					irmmsWellPartsSearchDTO.setMaintenance_date(maintenanceDt);
				}
				
				
				
				if (jObj.get("fieldName") != null) {
					fieldName = jObj.get("fieldName").toString();
					irmmsWellPartsSearchDTO.setFieldName(fieldName);
				}
				else
				{
					irmmsWellPartsSearchDTO.setFieldName(fieldName);
				}
				
				if(jObj.get("moduleName") != null){
					keyRegion = jObj.get("moduleName").toString();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//search_Data.setSearch_wells_data(search_wells_data);
		search_Data.setSearch_wells_data(mvtDAO.searchWellData(irmmsWellPartsSearchDTO));
		search_Data.setCustomer_name_data(mvtDAO.searchCustomerData(irmmsWellPartsSearchDTO));
		return search_Data;
	}
		
	public String uploadWellData(InputStream stream){

		List<WellDTO> lstWellData = getWellData();
		List<String> matchWell = new ArrayList<String>();
		List<String> unmatchWell = new ArrayList<String>();
        String status="Failure";
		 try {
			OPCPackage pkg = OPCPackage.open(stream);
			XSSFWorkbook workBook = new XSSFWorkbook(pkg);

			XSSFSheet dataSheet = workBook.getSheetAt(0);
			Iterator<Row> rows = dataSheet.rowIterator();
			//Skipping Header row for data validation
			rows.next();
			while (rows.hasNext())
			{
				XSSFRow row = (XSSFRow) rows.next();
				XSSFCell cell = row.getCell(0);
				cell.setCellType(1);
				String wellName = cell.getRichStringCellValue().toString();
				if(!"".equalsIgnoreCase(wellName))
				{
					for(int i=0;i<lstWellData.size();i++)
					{
						WellDTO wellData = lstWellData.get(i);
						if(wellName.equalsIgnoreCase(wellData.getWell_name()))
						{
							matchWell.add(wellName);
							break;
						}
					}
					if(!matchWell.contains(wellName))
					{
						unmatchWell.add(wellName);
					}
				}
				else
				{
					break;
				}
			}
			System.out.println("Match Well Size:::::: "+matchWell.size());
			System.out.println("Unmatch Well Size:::::: "+unmatchWell.size());
			UploadDTO wellDtl = new UploadDTO();
			String nullCheckWell = "";
			if(unmatchWell.size()==0)
			{
				Iterator<Row> updateRows = dataSheet.rowIterator();
				updateRows.next();
				while (updateRows.hasNext())
				{
					wellDtl = new UploadDTO();
					XSSFRow row = (XSSFRow) updateRows.next();
					Iterator<Cell> cells = row.cellIterator();
					nullCheckWell = "";
					XSSFRichTextString richTextString = null;
					nullCheckWell = row.getCell(0).getStringCellValue();
					if(null != nullCheckWell && !"".equalsIgnoreCase(nullCheckWell))
					{
						row.getCell(0).setCellType(1); //WELL_ID
						row.getCell(1).setCellType(1); //PART_NO
						row.getCell(2).setCellType(1); //SERIAL_NO
						row.getCell(3).setCellType(1); //PMS_DESCRIPTION
						row.getCell(4).setCellType(1); //PRODUCT_DESCRIPTION
						//row.getCell(5).setCellType(3); //LAST_MAINTENANCE_DATE
						//row.getCell(6).setCellType(3); //NEXT_MAINTENANCE_DATE

						for(WellDTO temp: lstWellData)
						{
							if(nullCheckWell.equalsIgnoreCase(temp.getWell_name()))
							{
								wellDtl.setWELL_NAME(temp.getWell_name());
								break;
							}
						}
						wellDtl.setWELL_NAME(row.getCell(0).getStringCellValue().toUpperCase());
						wellDtl.setPART_NO(row.getCell(1).getStringCellValue());
						wellDtl.setSERIAL_NO(row.getCell(2).getStringCellValue());
						wellDtl.setPMS_DESCRIPTION(row.getCell(3).getStringCellValue());
						wellDtl.setPRODUCT_DESCRIPTION(row.getCell(4).getStringCellValue());
						try{
							Date lastMaintDt = row.getCell(5).getDateCellValue();
							System.out.println("Original Date : "+lastMaintDt );
							Date nextMainDt = row.getCell(6).getDateCellValue();
							SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
							String ldt = sdf.format(lastMaintDt); 
							System.out.println("String converted : "+ldt);
							String ndt = sdf.format(nextMainDt); 
							System.out.println("String converted : "+ndt);
							wellDtl.setLAST_MAINTENANCE_DATE(ldt);
							wellDtl.setNEXT_MAINTENANCE_DATE(ndt);
						}catch(Exception e)
						{
							e.printStackTrace();
						}
					}

					if(nullCheckWell==null || "".equalsIgnoreCase(nullCheckWell))
					{
						break;
					}
					status = mvtDAO.uploadDataImport(wellDtl);

				}
			}
			else
			{
				StringBuilder strBldr = new StringBuilder();
				strBldr.append("The following well names were not found in our records. Please verify and try again: ");
				strBldr.append("<ul>");
				for(int i=0;i<unmatchWell.size();i++)
				{
					String wellname = unmatchWell.get(i);
					strBldr.append("<li>"+(i+1)+". "+wellname+"</li>");
				}
				strBldr.append("</ul>");
				status = strBldr.toString();
				System.out.println("Status -------------- "+status);
			}

		  } catch (Exception e) {
			e.printStackTrace();
		  }
		return status;
	}
	
	public List<OverdueDTO> getoverdue(){
		return mvtDAO.getoverdue();
	}
}
