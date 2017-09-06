package com.irmms.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.irmms.dto.OverdueDTO;
import com.irmms.util.SpringApplicationContext;


public class EmailAlertController implements StatefulJob  {


	MVTServiceIntf mvtService = (MVTServiceIntf) SpringApplicationContext
			.getBean("mvtService");
	
	VelocityEngine velocityEngine =(VelocityEngine) SpringApplicationContext
			.getBean("velocityEngine");; 
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
	      // Recipient's email ID needs to be mentioned.
	      String to = "thirumalai.ramasamy@igate.com";

	      // Sender's email ID needs to be mentioned
	      String from = "suriya.lalitha@ge.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.put("mail.smtp.host", "");
          properties.put("mail.smtp.port", "");
          properties.put("mail.user","");
          properties.put("mail.password", "");

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(properties);

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Overdue Well Details");
	         
	         Map<String, Object> usermodel = new HashMap<String, Object>(); 

	         List<OverdueDTO> overdue = mvtService.getoverdue();
	         List<OverdueDTO> overdue1 = new ArrayList<OverdueDTO>();
	         List<OverdueDTO> overdue2 = new ArrayList<OverdueDTO>();
	         List<OverdueDTO> overdue3 = new ArrayList<OverdueDTO>();
	         ListIterator<OverdueDTO> itr=overdue.listIterator(); 
	         while(itr.hasNext()){  
	  				OverdueDTO ovedto = itr.next();  
	  				if(Integer.parseInt(ovedto.getDAYS())<0){
	  					overdue1.add(ovedto);
	  				}
	  				if(Integer.parseInt(ovedto.getDAYS())==0){
	  					overdue2.add(ovedto);
	  				}
	  				if(Integer.parseInt(ovedto.getDAYS())>0){
	  					overdue3.add(ovedto);
	  				}
	 		 }
	         ListIterator<OverdueDTO> itr1=overdue1.listIterator();
	         while(itr1.hasNext()){  
	  				OverdueDTO ovedto = itr1.next();  
	  					ovedto.setDAYS(Integer.toString(Math.abs(Integer.parseInt(ovedto.getDAYS()))));
	         }
	         usermodel.put("overdueLst1",overdue1);
	         usermodel.put("overdueLst2",overdue2);
	         usermodel.put("overdueLst3",overdue3);
	         
	         final String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"templates/emailtemplate.html",usermodel);
	                 
	         // Send the actual HTML message, as big as you like
	         message.setContent(text, "text/html" );

	         // Send message
	         if(overdue1.size()!=0 || overdue2.size()!=0 || overdue3.size()!=0)
	         {
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	         }
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      } 

	}

}
