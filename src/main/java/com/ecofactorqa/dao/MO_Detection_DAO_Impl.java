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


public class MO_Detection_DAO_Impl {
	
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
	public static int coolHVACMode = 0;
	public static int heatHVACMode = 0;
	public static int collSetpoint = 0;
	public static int heatSetpoint = 0;
	public static List<String> thermostat_ids = new ArrayList<String>();
	public static String user_name = "exr524638";
	public static int coolHVACModeComcast = 0;
	public static int coolHVACModeComcastSetpoint = 0;
	public static int coolHVACModeComcastSetpointRounded = 0;
	public static int coolHVACModeComcastPermModeOn = 0;
	public static int coolHVACModeComcastPermModeOff = 0;
	public static int coolHVACModeComcastPermOn = 0;
	public static int coolHVACModeComcastSetpointPermOn = 0;
	public static int coolHVACModeComcastSetpointRoundedPermOn = 0;
	public static int coolHVACModeComcastSetpointFanAuto = 0;
	public static int coolHVACModeComcastSetpointFanOff = 0;
	public static int coolHVACModeComcastHeat = 0;
	public static int coolHVACModeComcastOff = 0;
	public static int heatHVACModeComcast = 0;
	public static int heatHVACModeComcastSetpoint = 0;
	public static int heatHVACModeComcastSetpointRounded = 0;
	
	
	public static void thermostatStateApi_efts_cool_hvac_mode(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=20 and algorithm_id=-10 and event_status='PROCESSED' "
		    		+ "and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACMode = result.getInt("thermostat_id");
	        }
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void thermostatStateApi_efts_cool_setpoint(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();
	        
	        String sql1 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=0 and algorithm_id=-10 and event_status='PROCESSED' "
	        		+ "and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result1 = statment.executeQuery(sql1);
	        while (result1.next()) {
	        	collSetpoint = result1.getInt("thermostat_id");
	        }
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void thermostatStateApi_efts_heat_hvac_mode(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

		    
	        String sql2 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=20 and algorithm_id=-10 and event_status='PROCESSED' "
	        		+ "and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";	        
	        ResultSet result2 = statment.executeQuery(sql2);
	        while (result2.next()) {
	        	heatHVACMode = result2.getInt("thermostat_id");
	        }      
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void thermostatStateApi_efts_heat_setpoint(int t_id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql3 = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=0 and algorithm_id=-10 and event_status='PROCESSED' "
	        		+ "and event_ee !=0 and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 50 SECOND)) and timestamp(now()) order by last_updated DESC limit 6;";        
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
	
	public static void moForComcastUserThermostatID() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        System.out.println("my sql is pervoe " + sql);
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcast = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void moForComcastUserHeatThermostatID() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        System.out.println("my sql is pervoe " + sql);
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	heatHVACModeComcast = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void moForComcastUserSetpoint() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastSetpoint = result.getInt("new_setting");
	        } 
	        coolHVACModeComcastSetpointRounded = (int) Math.round(coolHVACModeComcastSetpoint);
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserHeatSetpoint() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	heatHVACModeComcastSetpoint = result.getInt("new_setting");
	        } 
	        heatHVACModeComcastSetpointRounded = (int) Math.round(heatHVACModeComcastSetpoint);
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserPermModeOn() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=1 and algorithm_id=-21 and event_status='ANALYZED'"
	        		+ "and event_ee is NULL and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastPermModeOn = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserPermModeOff() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-21 and event_status='ANALYZED'"
	        		+ "and event_ee is NULL and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastPermModeOff = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserThermostatIDPermOn() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-11 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        System.out.println("my sql is pervoe " + sql);
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastPermOn = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static void moForComcastUserSetpointPermOn() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=0 and algorithm_id=-11 and event_status='ANALYZED'"
	        		+ "and event_ee !=0 and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastSetpointPermOn = result.getInt("new_setting");
	        } 
	        coolHVACModeComcastSetpointRoundedPermOn = (int) Math.round(coolHVACModeComcastSetpointPermOn);
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserSetpointFanModeAuto() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_range_data_p  where thermostat_id in (" + thermostat_ids1 + ") and fan_mode =4 and start_time between timestamp (DATE_sub(now(), interval 80 SECOND)) "
	        		+ "and timestamp(now()) limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastSetpointFanAuto = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserSetpointFanModeOff() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_range_data_p  where thermostat_id in (" + thermostat_ids1 + ") and fan_mode =5 and start_time between timestamp (DATE_sub(now(), interval 80 SECOND)) "
	        		+ "and timestamp(now()) limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastSetpointFanOff = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserHVACHeat() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=20 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee is NULL and action='heat_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastHeat = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void moForComcastUserHVACOff() {
		try {
			//get thermostats for a user
			MO_Detection_DAO_Impl.findThermostatIdByUserName();
			//convert list to string
			String thermostats = thermostat_ids.toString();
			//delete square brackets 
			String thermostat_ids1 = thermostats.replace("[", "").replace("]", "");
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url_apps,efts_db_user_apps,efts_db_pass_apps);
		    Statement statment = connection.createStatement();
		    
	        String sql = "SELECT * FROM ef_thermostat_event  where thermostat_id in (" + thermostat_ids1 + ") and event_phase=20 and algorithm_id=-10 and event_status='ANALYZED'"
	        		+ "and event_ee is NULL and action='off' and event_sys_time between timestamp (DATE_sub(now(), interval 60 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	coolHVACModeComcastOff = result.getInt("thermostat_id");
	        } 
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
