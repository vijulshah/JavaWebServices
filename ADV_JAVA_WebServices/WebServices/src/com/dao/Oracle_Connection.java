package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Oracle_Connection {

	public static void main(String[] args) throws ClassNotFoundException, SQLException 
	{
		Oracle_Connection.getConnection();
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException 
	{
		String username = "system";
		String password = "root";
		String drivername = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		
		Class.forName(drivername);
		Connection con = DriverManager.getConnection(url, username, password);
		
		if(con==null)
		{
			//System.out.println("Db not Connected");
		}
		else
		{
			//System.out.println("Db Connected");
		}
		
		return con;
	}

}