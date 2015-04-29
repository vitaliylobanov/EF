package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.ecofactorqa.util.DAOprop;

public class SPO_DAO_Impl {

	// db connection
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ef_11_db_url = DAOprop.ef11_plat_db;
	public static final String ef_11_db_user = DAOprop.ef11_plat_user;
	public static final String ef_11_db_pass = DAOprop.ef11_plat_pass;
	public static final String efts_db_url = DAOprop.efts_plat_db;
	public static final String efts_db_user = DAOprop.efts_plat_user;
	public static final String efts_db_pass = DAOprop.efts_plat_pass;

	public static void insertStartSpoEntry(int t_id, String next_phase_time_start, String date_setup_start, String execution_start_time_utc_start, String execution_end_time_utc_start, String mo_cutoff_time_utc_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, execution_end_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 190, 'CANCELED', 0, 0, 0, "
		    		+ "'" + next_phase_time_start + "', '" + date_setup_start + "', '" + execution_start_time_utc_start + "', '" + execution_end_time_utc_start + "', '" + mo_cutoff_time_utc_start + "', "
		    		+ "'2.0', 2, 'SPO')"; 
		    statment.executeUpdate(sql);
		    System.out.println(sql);
	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertEndSpoEntry(int t_id, String next_phase_time_end, String date_setup_end, String execution_start_time_utc_end, String mo_cutoff_time_utc_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 190, 'CANCELED', 0, 0, 0, "
		    		+ "'" + next_phase_time_end + "', '" + date_setup_end + "', '" + execution_start_time_utc_end + "', '" + mo_cutoff_time_utc_end + "', '2.0', 2, 'SPO')"; 
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
