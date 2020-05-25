package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Oracle_Connection;

public class SignUp extends HttpServlet {
	
	private static Connection con;
	private static PreparedStatement ps;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
		   
		  String s1=request.getParameter("name");
		  String s2=request.getParameter("email_id");
		  String s3=request.getParameter("password");
		 
		  try  
	        {  
			  con=Oracle_Connection.getConnection();
			  String psQuery = "insert into DEMO_USERS(name,email_id,password) values(?,?,?)"; 
			  ps = con.prepareStatement(psQuery);
			  ps.setString(1, s1);  
			  ps.setString(2,s2); 
			  ps.setString(3,s3); 
			  ps.executeUpdate(); 
			  PrintWriter out = response.getWriter();
	          out.println("User Registered");
			  con.close();  
	        }  
	        catch (Exception e) {  
	          
	          PrintWriter out = response.getWriter();
		      out.println("Error While Saving");
	          e.printStackTrace();  
	        }  
	}

}
