package com.controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.Oracle_Connection;

public class Login extends HttpServlet {
	
	private static Connection con;
	private static PreparedStatement ps;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        
        String email=request.getParameter("email");    
        String password=request.getParameter("password");  
        
        if(validate(email, password)){    
        	PrintWriter out = response.getWriter();
            out.println("Successfully Logged in");
         }    
         else{    
        	 PrintWriter out = response.getWriter();
             out.println("Email or Password Not Found");  
         }    
                
         }    

		public static boolean validate(String email_id,String password){    
			boolean status=false;    
			try{    
				
				con=Oracle_Connection.getConnection();
				String psQuery = "select * from DEMO_USERS where email_id=? and password=?"; 
				ps = con.prepareStatement(psQuery);
				ps.setString(1,email_id);    
				ps.setString(2,password);    
                
				ResultSet rs=ps.executeQuery();    
				status=rs.next();    
                    
			}catch(Exception e){}
			
			return status;    
       	}    
}
