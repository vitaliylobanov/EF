package com.ecofactorqa.util;



public class APIprop {
	public static final String THERMOSTAT_STATE_URL = "http://qa-plat-tc1.ecofactor.com:8089/services/ws/v1.0/thermostat/thermostat_id/state";
	public static final String SET_THERMOSTAT_AWAY_URL = "http://qa-plat-tc1.ecofactor.com:8089/services/ws/v1.0/thermostat/thermostat_id/away";
	public static final String SET_DAILY_SCHEDULE_UPDATE_URL = "http://qa-plat-tc1.ecofactor.com:8089/ws/v1.0/thermostat/thermostat_id/dailySchedule/daily_schedule_id";
	
	public static final String json_away_start_valid_data1 = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\":\"<end_time>\"}";
	public static final String json_away_start_valid_data = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-12-03T06:35:00+00:00\"}";
	public static final String json_away_update_valid_data = "{\"cool_setpoint\": 74,\"heat_setpoint\": 68,\"end_ts\": \"2015-12-03T17:05:00+00:00\"}";
	public static final String json_away_start_invalid_date_past = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2013-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_no_date = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_no_time = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T\"}";
	public static final String json_away_start_invalid_date_no_hours = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T35:00+00:00\"}";
	public static final String json_away_start_invalid_date_no_minutes = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:00+00:00\"}";
	public static final String json_away_start_invalid_date_no_seconds = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35+00:00\"}";
	public static final String json_away_start_invalid_date_negative_hours = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T-06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_negative_min = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:-35:00+00:00\"}";
	public static final String json_away_start_invalid_date_negative_sec = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:-00+00:00\"}";
	public static final String json_away_start_invalid_date_no_cap_T = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-0306:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_no_cap_T_S = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03S06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_space_date_time = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03 06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_two_digit_year = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"15-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_one_digit_month = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-7-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_one_digit_day = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-3T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_zero_year = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"0000-07-03T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_zero_month = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-00-03T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_zero_day = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-00T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_no_year = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"-07-03T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_no_month = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015--03T06:35:00+00:00\"}";
    public static final String json_away_start_invalid_date_no_day = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_date_one_digit_min = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:5:00+00:00\"}";
	public static final String json_away_start_invalid_date_one_digit_sec = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:0+00:00\"}";
	public static final String json_away_start_invalid_date_incorrect_time_format_semicolons = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06;35:00+00:00\"}";
	public static final String json_away_start_invalid_date_incorrect_time_format_dash = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35-00+00:00\"}";
	public static final String json_away_start_invalid_date_incorrect_time_format_dot = "{\"cool_setpoint\": 80.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06.35.00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_empty = "{\"cool_setpoint\": ,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_zero = "{\"cool_setpoint\": 0,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_null = "{\"cool_setpoint\": null,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_one = "{\"cool_setpoint\": 1,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_negative = "{\"cool_setpoint\": -65,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_five_digit = "{\"cool_setpoint\": 84440.01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_coma = "{\"cool_setpoint\": 80,01,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_no_coma_at_the_end = "{\"cool_setpoint\": 80.01\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_boundary_max = "{\"cool_setpoint\": 100,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_boundary_min = "{\"cool_setpoint\": 17,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_cool_setpoint_char = "{\"cool_setpoint\": sfs67,\"heat_setpoint\": 65.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_empty = "{\"cool_setpoint\": 81,\"heat_setpoint\": ,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_zero = "{\"cool_setpoint\": 81,\"heat_setpoint\": 0,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_null = "{\"cool_setpoint\": 81,\"heat_setpoint\": null,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_one = "{\"cool_setpoint\": 81,\"heat_setpoint\": 1,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_negative = "{\"cool_setpoint\": 81,\"heat_setpoint\": -65,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_five_digit = "{\"cool_setpoint\": 81,\"heat_setpoint\": 644435.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_coma = "{\"cool_setpoint\": 81,\"heat_setpoint\": 65,01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_no_coma_at_the_end = "{\"cool_setpoint\": 81,\"heat_setpoint\": 65.01\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_boundary_max = "{\"cool_setpoint\": 81,\"heat_setpoint\": 100,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_boundary_min = "{\"cool_setpoint\": 81,\"heat_setpoint\": 17.01,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	public static final String json_away_start_invalid_heat_setpoint_char = "{\"cool_setpoint\": 76,\"heat_setpoint\": dgdg67,\"end_ts\": \"2015-07-03T06:35:00+00:00\"}";
	
	
	public static final String json_state_valid_hvac_mode_cool = "{\"hvac_mode\":\"cool\"}";
	public static final String json_state_valid_hvac_mode_heat = "{\"hvac_mode\":\"heat\"}";
	public static final String json_state_valid_coolsetpoint = "{\"cool_setpoint\": 71}";
	public static final String json_state_valid_coolsetpointSPO = "{\"cool_setpoint\": 76}";
	public static final String json_state_valid_coolsetpoint_fanmode = "{\"cool_setpoint\": 71,\"fan_mode\":\"auto\"}";	
	public static final String json_state_valid_heatsetpoint = "{\"heat_setpoint\": 64}";
	public static final String json_state_valid_heatsetpoint_fanmode = "{\"heat_setpoint\": 64,\"fan_mode\":\"on\"}";
	
	public static final String json_state_invalid_hvac_mode_empty = "{\"hvac_mode\":\"\"}";
    public static final String json_state_invalid_hvac_mode_emptyspace = "{\"hvac_mode\":\" \"}";
    public static final String json_state_invalid_hvac_mode_null = "{\"hvac_mode\":\"null\"}";
    public static final String json_state_invalid_hvac_mode_numbers = "{\"hvac_mode\":\"1234\"}";
    public static final String json_state_invalid_hvac_mode_multicommasep = "{\"hvac_mode\":\"heat,cool\"}";
    public static final String json_state_invalid_hvac_mode_multitabsep = "{\"hvac_mode\":\"heat|cool\"}";
    public static final String json_state_invalid_hvac_mode_noquotes = "{\"hvac_mode\":cool}";
    public static final String json_state_invalid_hvac_mode_noquotesempty = "{\"hvac_mode\":}";
    public static final String json_state_invalid_hvac_mode_noquotesnull = "{\"hvac_mode\":null}";
    public static final String json_state_invalid_hvac_mode_singlequotes = "{\"hvac_mode\":\'cool\'}";
    public static final String json_state_invalid_hvac_mode_nonexisting = "{\"hvac_mode\":\"coooooolllll\"}";
    public static final String json_state_invalid_hvac_mode_uppercase = "{\"hvac_mode\":\"COOL\"}";
    public static final String json_state_invalid_fan_mode_empty = "{\"fan_mode\":\"\"}";
    public static final String json_state_invalid_fan_mode_emptyspace = "{\"fan_mode\":\" \"}";
    public static final String json_state_invalid_fan_mode_null = "{\"fan_mode\":\"null\"}";
    public static final String json_state_invalid_fan_mode_numbers = "{\"fan_mode\":\"1234\"}";
    public static final String json_state_invalid_fan_mode_multicommasep = "{\"fan_mode\":\"on,auto\"}";
    public static final String json_state_invalid_fan_mode_multitabsep = "{\"fan_mode\":\"on|auto\"}";
    public static final String json_state_invalid_fan_mode_noquotes = "{\"fan_mode\":on}";
    public static final String json_state_invalid_fan_mode_noquotesempty = "{\"fan_mode\":}";
    public static final String json_state_invalid_fan_mode_noquotesnull = "{\"fan_mode\":null}";
    public static final String json_state_invalid_fan_mode_singlequotes = "{\"fan_mode\":\'auto\'}";
    public static final String json_state_invalid_fan_mode_nonexisting = "{\"fan_mode\":\"auuuutooo\"}";
    public static final String json_state_invalid_fan_mode_uppercase = "{\"fan_mode\":\"AUTO\"}";
    public static final String json_state_invalid_coolsetpoint_empty = "{\"cool_setpoint\":}";
    public static final String json_state_invalid_coolsetpoint_zero = "{\"cool_setpoint\":0}";
    public static final String json_state_invalid_coolsetpoint_null = "{\"cool_setpoint\":null}";
    public static final String json_state_invalid_coolsetpoint_boundarylow = "{\"cool_setpoint\":11}";
    public static final String json_state_invalid_coolsetpoint_boundaryhigh = "{\"cool_setpoint\":145}";
    public static final String json_state_invalid_coolsetpoint_decimalcomma = "{\"cool_setpoint\":67,43}";
    public static final String json_state_invalid_coolsetpoint_decimaldot = "{\"cool_setpoint\":67.57}";
    public static final String json_state_invalid_coolsetpoint_doublequotes = "{\"cool_setpoint\":\"71\"}";
    public static final String json_state_invalid_coolsetpoint_singlequotes = "{\"cool_setpoint\":\'71\'}";
    public static final String json_state_invalid_heatsetpoint_empty = "{\"heat_setpoint\":}";
    public static final String json_state_invalid_heatsetpoint_zero = "{\"heat_setpoint\":0}";
    public static final String json_state_invalid_heatsetpoint_null = "{\"heat_setpoint\":null}";
    public static final String json_state_invalid_heatsetpoint_boundarylow = "{\"heat_setpoint\":2}";
    public static final String json_state_invalid_heatTsetpoint_boundaryhigh = "{\"heat_setpoint\":99}";
    public static final String json_state_invalid_heatsetpoint_decimalcomma = "{\"heat_setpoint\":65,87}";
    public static final String json_state_invalid_heatsetpoint_decimaldot = "{\"heat_setpoint\":65.53}";
    public static final String json_state_invalid_heatsetpoint_doublequotes = "{\"heat_setpoint\":\"62\"}";
    public static final String json_state_invalid_heatsetpoint_singlequotes = "{\"heat_setpoint\":\'64\'}";
    
    
    public static final String json_daily_schedule_update = "{\"name\": \"Same each day\",\"intervals\": [{ \"name\":\"Other\","
    		+ "\"start_time\": \"06:00\",\"cool_setpoint\":72,\"heat_setpoint\": 67}]}"; 
    public static final String json_daily_schedule_update_second_time = "{\"name\": \"Same each day\",\"intervals\": [{ \"name\":\"Other\","
    		+ "\"start_time\": \"06:00\",\"cool_setpoint\":70,\"heat_setpoint\": 67},{\"name\":\"Other\","
    		+ "\"start_time\": \"<schedule_second_interval_start_time>\",\"cool_setpoint\":75,\"heat_setpoint\": 67}]}";

}
