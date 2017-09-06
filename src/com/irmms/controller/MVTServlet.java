package com.irmms.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * Servlet implementation class MVTServlet
 */
public class MVTServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MVTServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequestWrapper request, HttpServletResponseWrapper response) throws ServletException, IOException {

		try {
			
		System.out.println("reached MVTServlet ");
		response.setContentType("text/html");
		String strSSOID = "502324486";
		ArrayList lstSSOID =new ArrayList();
		lstSSOID.add("502324486");
		
		if(lstSSOID.contains(strSSOID))
		{
			response.sendRedirect("pages/HomePage.html");
			
		}
		else
		{
			response.sendRedirect("pages/Logout.html");
		}
		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequestWrapper request, HttpServletResponseWrapper response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
