package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import com.ecofactorqa.util.DAOprop;

public class ef11_plat_dbconnection {
	
    //db connection
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ef_11_db_url = DAOprop.ef11_plat_db;
	public static final String ef_11_db_user = DAOprop.ef11_plat_user;
	public static final String ef_11_db_pass = DAOprop.ef11_plat_pass;
	public static final String efts_db_url = DAOprop.efts_plat_db;
	public static final String efts_db_user = DAOprop.efts_plat_user;
	public static final String efts_db_pass = DAOprop.efts_plat_pass;
	
	//ef11 var to verify
	public static int start_away_thermostat_id_algo_control=0;
	public static int start_away_thermostat_id_program_log=0;
	public static String start_away_thermostat_id_program;
	public static int start_away_user_id_program=0;
	
	//efts var to verify
	

	

	public static void test_ef11() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
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
	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	    
		
	}
	

	public static void start_away_ef_11(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control where thermostat_id= '" + t_id + "' and algorithm_id=-20 and thermostat_algorithm_status='ACTIVE' order by last_updated DESC limit 2;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_away_thermostat_id_algo_control = result.getInt("thermostat_id");
	        }
	        
	        String sql1 = "SELECT * FROM ef_thermostat_program_log where thermostat_id= '" + t_id + "' and program_type='USER_AWAY' and program_status='ACTIVE' order by last_updated DESC limit 2;";
	        ResultSet result1 = statment.executeQuery(sql1);
	        while (result1.next()) {
	        	start_away_thermostat_id_program_log = result1.getInt("thermostat_id");
	        }
	        
            String sql2 = "SELECT * FROM ef_program where program_id in(SELECT program_id FROM ef_thermostat_program where thermostat_id= '" + t_id + "' and  thermostat_program_status='ACTIVE') and program_type='USER_AWAY';";
	        ResultSet result2 = statment.executeQuery(sql2);
	        while (result2.next()) {
	        	start_away_thermostat_id_program = result2.getString("program_type");
	        	start_away_user_id_program = result2.getInt("user_id");
	        }         	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void start_away_efts(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=50 and event_status='PROCESSED' order by last_updated DESC limit 2;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_away_thermostat_id_algo_control = result.getInt("thermostat_id");
	        }
	        
	        String sql1 = "SELECT * FROM ef_thermostat_program_log where thermostat_id= '" + t_id + "' and program_type='USER_AWAY' and program_status='ACTIVE' order by last_updated DESC limit 2;";
	        ResultSet result1 = statment.executeQuery(sql1);
	        while (result1.next()) {
	        	start_away_thermostat_id_program_log = result1.getInt("thermostat_id");
	        }
	        
            String sql2 = "SELECT * FROM ef_program where program_id in(SELECT program_id FROM ef_thermostat_program where thermostat_id= '" + t_id + "' and  thermostat_program_status='ACTIVE') and program_type='USER_AWAY';";
	        ResultSet result2 = statment.executeQuery(sql2);
	        while (result2.next()) {
	        	start_away_thermostat_id_program = result2.getString("program_type");
	        	start_away_user_id_program = result2.getInt("user_id");
	        }         	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}