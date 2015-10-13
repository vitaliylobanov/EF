package com.ecofactorqa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
	
	//var to verify
    public static int start_spo_thermostat_id_thermostat_event = 0;
    public static int start_spo_thermostat_id_thermostat_algoritm = 0;
    public static int end_spo_thermostat_id_thermostat_event = 0;
    public static int end_spo_thermostat_id_thermostat_algoritm = 0;
    public static int end_spo_thermostat_id_mo = 0;
    public static int start_spo_thermostat_id_thermostat_event_heat = 0;
    public static int start_spo_thermostat_id_thermostat_algoritm_heat = 0;
    public static int end_spo_thermostat_id_thermostat_event_heat = 0;
    public static int end_spo_thermostat_id_thermostat_algoritm_heat =0 ;
    public static int update_spo_thermostat_id_thermostat_algoritm = 0;
    
    

	public static void insertStartSpoEntry(int t_id, int event_ee_start, String next_phase_time_start, String date_setup_start, String execution_start_time_utc_start, String execution_end_time_utc_start, String mo_cutoff_time_utc_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control_short (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, execution_end_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 190, 0, 0, '" + event_ee_start + "', 0, "
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

		    String sql = "INSERT INTO ef_thermostat_algo_control_short (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 190, 0, 0, 0, 0, "
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
	
	public static void start_SPO_efts(int t_id, int event_ee_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and algorithm_id=190 and event_ee= '" + event_ee_start + "' and event_status='PROCESSED' and event_sys_time between timestamp (DATE_sub(now(), interval 600 SECOND)) and timestamp(now()) order by event_sys_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_spo_thermostat_id_thermostat_event = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void start_SPO_ef11(int t_id, int setting_phase_0_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control_short where thermostat_id= '" + t_id + "' and algorithm_id=190 and setting_phase_0= '" + setting_phase_0_start + "' and thermostat_algorithm_status=9 and next_phase_time between timestamp (DATE_sub(now(), interval 600 SECOND)) and timestamp(now()) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_spo_thermostat_id_thermostat_algoritm = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void end_SPO_efts(int t_id, int event_ee_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and algorithm_id=190 and event_ee= '" + event_ee_end + "' and event_status='PROCESSED' and event_sys_time between timestamp (DATE_sub(now(), interval 720 SECOND)) and timestamp(now()) order by event_sys_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	end_spo_thermostat_id_thermostat_event = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void end_SPO_ef11(int t_id, int setting_phase_0_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control_short where thermostat_id= '" + t_id + "' and algorithm_id=190 and setting_phase_0= '" + setting_phase_0_end + "' and thermostat_algorithm_status=9 and next_phase_time between timestamp (DATE_sub(now(), interval 720 SECOND)) and timestamp(now()) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	end_spo_thermostat_id_thermostat_algoritm = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void end_SPO_MO_efts(int t_id, int coolSetpointSPOEnd) {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and event_phase=0 and algorithm_id=-10 and new_setting= '" + coolSetpointSPOEnd + "' and event_status='PROCESSED' and action='cool_setting' and event_sys_time between timestamp (DATE_sub(now(), interval 750 SECOND)) and timestamp(now()) order by last_updated DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	end_spo_thermostat_id_mo = result.getInt("thermostat_id");
	        }   	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertStartSpoEntryHeat(int t_id, int event_ee_start, String next_phase_time_start, String date_setup_start, String execution_start_time_utc_start, String execution_end_time_utc_start, String mo_cutoff_time_utc_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control_short (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, execution_end_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 191, 0, 0, '" + event_ee_start + "', 0, "
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
	
	public static void insertEndSpoEntryHeat(int t_id, String next_phase_time_end, String date_setup_end, String execution_start_time_utc_end, String mo_cutoff_time_utc_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

		    String sql = "INSERT INTO ef_thermostat_algo_control_short (thermostat_id, algorithm_id, thermostat_algorithm_status, algorithm_phase, "
		    		+ "setting_phase_0, setting_prev_phase, next_phase_time, date_setup, execution_start_time_utc, mo_cutoff_time_utc, mo_recovery, mo_blackout, actor) "
		    		+ "VALUES ('" + t_id + "', 191, 0, 0, 0, 0, "
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
	
	public static void start_SPO_efts_heat(int t_id, int event_ee_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and algorithm_id=191 and event_ee= '" + event_ee_start + "' and event_status='PROCESSED' and event_sys_time between timestamp (DATE_sub(now(), interval 600 SECOND)) and timestamp(now()) order by event_sys_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_spo_thermostat_id_thermostat_event_heat = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void start_SPO_ef11_heat(int t_id, int setting_phase_0_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control_short where thermostat_id= '" + t_id + "' and algorithm_id=191 and setting_phase_0= '" + setting_phase_0_start + "' and thermostat_algorithm_status=9 and next_phase_time between timestamp (DATE_sub(now(), interval 600 SECOND)) and timestamp(now()) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	start_spo_thermostat_id_thermostat_algoritm_heat = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void end_SPO_efts_heat(int t_id, int event_ee_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(efts_db_url,efts_db_user,efts_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_event where thermostat_id= '" + t_id + "' and algorithm_id=191 and event_ee= '" + event_ee_end + "' and event_status='PROCESSED' and event_sys_time between timestamp (DATE_sub(now(), interval 720 SECOND)) and timestamp(now()) order by event_sys_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	end_spo_thermostat_id_thermostat_event_heat = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void end_SPO_ef11_heat(int t_id, int setting_phase_0_end) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control_short where thermostat_id= '" + t_id + "' and algorithm_id=191 and setting_phase_0= '" + setting_phase_0_end + "' and thermostat_algorithm_status=9 and next_phase_time between timestamp (DATE_sub(now(), interval 720 SECOND)) and timestamp(now()) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	end_spo_thermostat_id_thermostat_algoritm_heat = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public static void update_SPO_ef11(int t_id, int setting_phase_0_start) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(ef_11_db_url,ef_11_db_user,ef_11_db_pass);
		    Statement statment = connection.createStatement();

	        String sql = "SELECT * FROM ef_thermostat_algo_control_short where thermostat_id= '" + t_id + "' and algorithm_id=190 and setting_phase_0= '" + setting_phase_0_start + "' and thermostat_algorithm_status=0 and next_phase_time between  timestamp(now()) and timestamp (DATE_ADD(now(), interval 600 SECOND)) order by next_phase_time DESC limit 1;";	        
	        ResultSet result = statment.executeQuery(sql);
	        while (result.next()) {
	        	update_spo_thermostat_id_thermostat_algoritm = result.getInt("thermostat_id");
	        }      	        
		} 
		catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
