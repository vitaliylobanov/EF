package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.testng.annotations.Test;

import com.ecofactorqa.util.DAOprop;

public class ef11_plat_dbconnection {

	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String db_url = DAOprop.ef11_plat_db;
	public static final String db_user = DAOprop.ef11_plat_user;
	public static final String db_pass = DAOprop.ef11_plat_pass;

	
	@Test
	public static void connect_to_ef11() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(db_url,db_user,db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM .ef_user order by last_updated DESC limit 5";
	        ResultSet result = statment.executeQuery(sql);
	        
	        ResultSetMetaData rsmd = result.getMetaData();
	        int columnsNumber = rsmd.getColumnCount();
	      
	        
	        while (result.next()) {
	            for (int i = 1; i <= columnsNumber; i++) {
	                if (i > 1) System.out.print(",  ");
	                String columnValue = result.getString(i);
	                System.out.print(columnValue + " " + rsmd.getColumnName(i));
	            }
	            System.out.println("");
	        }
	        
	        
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	    
		
	}
}