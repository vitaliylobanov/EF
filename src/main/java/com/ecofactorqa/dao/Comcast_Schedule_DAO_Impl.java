package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ecofactorqa.util.DAOprop;


public class Comcast_Schedule_DAO_Impl {
	
    //db connection
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ef_11_db_url = DAOprop.ef11_plat_db;
	public static final String ef_11_db_user = DAOprop.ef11_plat_user;
	public static final String ef_11_db_pass = DAOprop.ef11_plat_pass;
	public static final String efts_db_url = DAOprop.efts_plat_db;
	public static final String efts_db_user = DAOprop.efts_plat_user;
	public static final String efts_db_pass = DAOprop.efts_plat_pass;
	
	//apps
	public static final String ef_11_db_url_apps = DAOprop.ef11_apps_db;
	public static final String ef_11_db_user_apps = DAOprop.ef11_apps_user;
	public static final String ef_11_db_pass_apps = DAOprop.ef11_apps_pass;
	public static final String efts_db_url_apps = DAOprop.efts_apps_db;
	public static final String efts_db_user_apps = DAOprop.efts_apps_user;
	public static final String efts_db_pass_apps = DAOprop.efts_apps_pass;
	
	//efts var to verify
	public static int numberOfActiveRowsForSchedule = 0;
	public static List<String> thermostat_ids = new ArrayList<String>();
	public static String user_name = "exr524638";

	
	
	public static void findThermostatIdByUserName() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url_apps,ef_11_db_user_apps,ef_11_db_pass_apps);
		    Statement statment = connection.createStatement();

	        String sql = "select t.thermostat_id from ef_thermostat t,ef_user u,ef_location l, ef_gateway g "
	        		+ "where user_name like '" + user_name + "' and l.user_id=u.user_id and t.location_id=l.location_id and t.gateway_id=g.gateway_id;";     
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	//add all results to a list
	        	thermostat_ids.add(result.getString("thermostat_id"));
	        } 
	        System.out.println(Arrays.toString(thermostat_ids.toArray()));
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void comcastSchedule() {
		try {
			//get thermostats for a user
			Comcast_Schedule_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url_apps,ef_11_db_user_apps,ef_11_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "select count(*) as number from ef_thermostat_program_log where thermostat_id in (" + thermostat_ids1 + ") and program_status='ACTIVE';";
	        System.out.println("my sql is pervoe " + sql);
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	numberOfActiveRowsForSchedule = result.getInt("number");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
