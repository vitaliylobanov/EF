package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecofactorqa.util.DAOprop;

public class Real_Temp_DAO_Impl {

	// db connection
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ef_11_db_url = DAOprop.ef11_plat_db;
	public static final String ef_11_db_user = DAOprop.ef11_plat_user;
	public static final String ef_11_db_pass = DAOprop.ef11_plat_pass;
	public static final String efts_db_url = DAOprop.efts_plat_db;
	public static final String efts_db_user = DAOprop.efts_plat_user;
	public static final String efts_db_pass = DAOprop.efts_plat_pass;
	
	//var to verify
    public static int start_realtemp_thermostat_id_thermostat_event = 0;
    public static int reschedule_real_temp_thermostat_id_thermostat_algoritm = 0;  

	public static void insertStartRealTempEntry(int t_id, String next_phase_time_start, String date_setup_start, String execution_start_time_utc_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, next_phase_time, date_setup, "
		    		+ "execution_start_time_utc) "
		    		+ "VALUES ('" + t_id + "', 130, 'ACTIVE', 0,'" + next_phase_time_start + "', '" + date_setup_start + "', '" + execution_start_time_utc_start + "')"; 
		    statment.executeUpdate(sql);
		    System.out.println(sql);
	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void start_real_temp_efts(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and algorithm_id=130 and event_status='PROCESSED' and event_sys_time between timestamp (DATE_sub(now(), interval 300 SECOND)) and timestamp(now()) order by event_sys_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_realtemp_thermostat_id_thermostat_event = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void real_temp_rescheduled_ef11(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control where thermostat_id= '" + t_id + "' and algorithm_id=130 and thermostat_algorithm_status='ACTIVE' and next_phase_time between timestamp (DATE_add(now(), interval 23 HOUR)) and timestamp (DATE_add(now(), interval '23:59' HOUR_MINUTE)) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	reschedule_real_temp_thermostat_id_thermostat_algoritm = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cancel_active_real_temp(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "UPDATE ef_thermostat_algo_control SET thermostat_algorithm_status='INACTIVE' where thermostat_id= '" + t_id + "' and algorithm_id=130;";	        
	        statment.executeUpdate(sql);
	        System.out.println(sql);
        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
