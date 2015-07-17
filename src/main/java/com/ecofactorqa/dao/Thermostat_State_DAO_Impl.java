package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecofactorqa.util.DAOprop;


public class Thermostat_State_DAO_Impl {
	
    //db connection
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ef_11_db_url = DAOprop.ef11_plat_db;
	public static final String ef_11_db_user = DAOprop.ef11_plat_user;
	public static final String ef_11_db_pass = DAOprop.ef11_plat_pass;
	public static final String efts_db_url = DAOprop.efts_plat_db;
	public static final String efts_db_user = DAOprop.efts_plat_user;
	public static final String efts_db_pass = DAOprop.efts_plat_pass;
	
	//efts var to verify
	public static int coolHVACMode=0;
	public static int heatHVACMode=0;
	public static int collSetpoint=0;
	public static int heatSetpoint=0;
	
	public static void thermostatStateApi_efts(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=20 and algorithm_id=-10 and event_status='PROCESSED' and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACMode = result.getInt("thermostat_id");
	        }
	        
	        String sql1 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=0 and algorithm_id=-10 and event_status='PROCESSED' and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result1 = statment.executeQuery(sql1);
	        while (result1.next()) {
	        	collSetpoint = result1.getInt("thermostat_id");
	        }
	        
	        String sql2 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=20 and algorithm_id=-10 and event_status='PROCESSED' and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result2 = statment.executeQuery(sql2);
	        while (result2.next()) {
	        	heatHVACMode = result2.getInt("thermostat_id");
	        }      
	        
	        String sql3 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=0 and algorithm_id=-10 and event_status='PROCESSED' and event_ee !=0 and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";        
	        ResultSet result3 = statment.executeQuery(sql3);
	        while (result3.next()) {
	        	heatSetpoint = result3.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
