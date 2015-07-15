package com.ecofactor.spo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.dao.Away_DAO_Impl;
import com.ecofactorqa.dao.SPO_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.JsonUtil;
import com.ecofactorqa.util.WaitUtil;

public class SPO {
	
	private Client client;
	
    //variables for assertions spo
	final int t_id = 31437;
	final int event_ee_start = 2;
	final int setting_phase_0_start = event_ee_start;
	final int event_ee_end = 0;
	final int setting_phase_0_end = event_ee_end;
    final int coolSetpointSPOEndMO = 76;
	//away int for assertions
    int end_away=0;
	final int u_id=23531;
	
    String next_phase_time_start;
    String date_setup_start;
    String execution_start_time_utc_start;
    String execution_end_time_utc_start;
    String mo_cutoff_time_utc_start;
    String next_phase_time_end;
    String date_setup_end;
    String execution_start_time_utc_end;
    String mo_cutoff_time_utc_end;
    //away string assertions
	final String user_away_status="USER_AWAY";
    
    //state api t_stat change
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", "31437");
	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", "31437");
	private String dailyScheduleUpdate = APIprop.SET_DAILY_SCHEDULE_UPDATE_URL
			.replaceFirst("thermostat_id", "31437").replaceFirst("daily_schedule_id","259955");
	
	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	

	@Test //PLAT - 482
	public void createSpoMobeforeMOcutoffTime(){
		//verify t_stat is in schedule, plus do mo
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertFalse(content.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason should not be EE");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
		
        //set cool setpoint 
        WaitUtil.tinyWait();
		String jsonCoolSetpointChange = APIprop.json_state_valid_coolsetpoint;
		Invocation.Builder invocationBuilderCoolSetpoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolSetpoint = invocationBuilderCoolSetpoint.put(Entity.json(jsonCoolSetpointChange));
		WaitUtil.tinyWait();
		Assert.assertTrue(responseCoolSetpoint.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpoint.getStatus());
		System.out.println(responseCoolSetpoint);
		WaitUtil.tinyWait();
		
		Invocation.Builder invocationBuilderState = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseStateThermostat = invocationBuilderState.get();
		String contentStateThermostat = responseStateThermostat.readEntity(String.class);
		Assert.assertTrue(responseStateThermostat.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostat.getStatus());
		Assert.assertTrue(contentStateThermostat.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(contentStateThermostat.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
		Assert.assertTrue(contentStateThermostat.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(contentStateThermostat.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		System.out.println("Current Thermostat state is " + contentStateThermostat);
		WaitUtil.tinyWait();
		
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 2); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 7); // Adding 7 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":73"),"Expected cool_setpoint 73"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		Assert.assertTrue(content2.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT -
	public void createSpoForHeatThermostat_heat_191(){
		//set t_stat to heat mode
		String jsonString = APIprop.json_state_valid_hvac_mode_heat;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode Heat");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String heat_setpoint = knownstate.get("heat_setpoint").toString();
        System.out.println("heat_setpoint--- " + heat_setpoint);
		
		WaitUtil.tinyWait();
		WaitUtil.tinyWait();
		
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 3); // Adding 3 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 10); // Adding 10 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntryHeat(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntryHeat(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();

		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode HEAT");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		//Assert.assertTrue(content1.contains("\"cool_setpoint\":73"),"Expected cool_setpoint 73"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts_heat(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event_heat, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11_heat(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm_heat, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode Heat");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
//		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason schedule");
//		Assert.assertTrue(content2.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts_heat(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event_heat, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11_heat(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm_heat, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT - 486
	public void createSpoMODuringSPO(){
		//verify t_stat is in schedule
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertFalse(content.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason should not be EE");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
        
        //set cool setpoint 
        WaitUtil.tinyWait();
		String jsonCoolSetpointChange = APIprop.json_state_valid_coolsetpoint;
		Invocation.Builder invocationBuilderCoolSetpoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolSetpoint = invocationBuilderCoolSetpoint.put(Entity.json(jsonCoolSetpointChange));
		WaitUtil.tinyWait();
		Assert.assertTrue(responseCoolSetpoint.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpoint.getStatus());
		System.out.println(responseCoolSetpoint);
		WaitUtil.tinyWait();
		
		Invocation.Builder invocationBuilderState = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseStateThermostat = invocationBuilderState.get();
		String contentStateThermostat = responseStateThermostat.readEntity(String.class);
		Assert.assertTrue(responseStateThermostat.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostat.getStatus());
		Assert.assertTrue(contentStateThermostat.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(contentStateThermostat.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
		Assert.assertTrue(contentStateThermostat.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(contentStateThermostat.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		System.out.println("Current Thermostat state is " + contentStateThermostat);
		WaitUtil.tinyWait();
				
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 2); // Adding 3 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 10); // Adding 8 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.veryHugeWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":73"),"Expected cool_setpoint 73"); // 73F = 71F (is latest mo line 95) + 2F (line 39 SPO_DAO_Impl) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
			
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//MO during shed phase
        WaitUtil.oneMinuteWait();
		String jsonCoolSetpointChangeSPO = APIprop.json_state_valid_coolsetpointSPO;
		Invocation.Builder invocationBuilderCoolSetpointSPO = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolSetpointSPO = invocationBuilderCoolSetpointSPO.put(Entity.json(jsonCoolSetpointChangeSPO));
		WaitUtil.tinyWait();
		Assert.assertTrue(responseCoolSetpointSPO.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpointSPO.getStatus());
		System.out.println(responseCoolSetpointSPO);
		WaitUtil.tinyWait();
		
		Invocation.Builder invocationBuilderStateSPO = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseStateThermostatSPO = invocationBuilderStateSPO.get();
		String contentStateThermostatSPO = responseStateThermostatSPO.readEntity(String.class);
		Assert.assertTrue(responseStateThermostatSPO.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostatSPO.getStatus());
		Assert.assertTrue(contentStateThermostatSPO.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(contentStateThermostatSPO.contains("\"cool_setpoint\":" + coolSetpointSPOEndMO),"Expected cool_setpoint 76");
		Assert.assertTrue(contentStateThermostatSPO.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(contentStateThermostatSPO.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		System.out.println("Current Thermostat state is " + contentStateThermostatSPO);
		WaitUtil.tinyWait();
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		WaitUtil.threeMinutesWait();
		
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		Assert.assertTrue(content2.contains("\"cool_setpoint\":" + coolSetpointSPOEndMO),"Expected cool_setpoint 76");
		System.out.println("Current Thermostat state is " + content2);

		//efts end db verification for mo
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_MO_efts(t_id, coolSetpointSPOEndMO);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_mo, t_id);
				
		//ef11 end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT - 488
	public void createSpoScheduleChangeMOcutoffTime(){
		//verify t_stat is in schedule, plus do mo
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertFalse(content.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason should not be EE");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
		
        //set cool setpoint 
        WaitUtil.tinyWait();
		String jsonCoolSetpointChange = APIprop.json_state_valid_coolsetpoint;
		Invocation.Builder invocationBuilderCoolSetpoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolSetpoint = invocationBuilderCoolSetpoint.put(Entity.json(jsonCoolSetpointChange));
		WaitUtil.tinyWait();
		Assert.assertTrue(responseCoolSetpoint.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpoint.getStatus());
		System.out.println(responseCoolSetpoint);
		WaitUtil.tinyWait();
		
		Invocation.Builder invocationBuilderState = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseStateThermostat = invocationBuilderState.get();
		String contentStateThermostat = responseStateThermostat.readEntity(String.class);
		Assert.assertTrue(responseStateThermostat.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostat.getStatus());
		Assert.assertTrue(contentStateThermostat.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(contentStateThermostat.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
		Assert.assertTrue(contentStateThermostat.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(contentStateThermostat.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		System.out.println("Current Thermostat state is " + contentStateThermostat);
		WaitUtil.tinyWait();
		
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 9); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 14); // Adding 7 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		//making schedule change
		WaitUtil.oneMinuteWait();
		WaitUtil.oneMinuteWait();
		String jsonStringSchedule = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACModeAfterMOcutoff = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacModeAfterMoCutoff = invocationBuilderHVACModeAfterMOcutoff.put(Entity.json(jsonStringSchedule));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacModeAfterMoCutoff.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacModeAfterMoCutoff.getStatus());
		System.out.println(responseCoolHvacModeAfterMoCutoff);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilderAfterMO = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response3 = invocationBuilderAfterMO.get();
		String contentAfterMO = response3.readEntity(String.class);
		Assert.assertTrue(response3.getStatus() == 200,"Expected status 200. Actual status is :"+ response3.getStatus());
		Assert.assertTrue(contentAfterMO.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(contentAfterMO.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");

		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		WaitUtil.hugeWait();
		WaitUtil.oneMinuteWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":73"),"Expected cool_setpoint 73"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		//Assert.assertTrue(content2.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT - 493
	public void createSpoAwayBeforeSPOstartFoSPODuration(){
		//verify t_stat is in schedule, plus do mo
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertFalse(content.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason should not be EE");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
        
        //Start_AWAY
        //set custom end time for away
		String jsonStringAway = APIprop.json_away_start_valid_data1;
        SimpleDateFormat dateFormatUTCAway = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        dateFormatUTCAway.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendarAWAY = Calendar.getInstance();
        calendarAWAY.setTime(new Date()); // Now use today date.
        calendarAWAY.add(Calendar.MINUTE, 12); // Adding 12 min
        String output = dateFormatUTCAway.format(calendarAWAY.getTime()); 
        jsonStringAway = jsonStringAway.replaceFirst("<end_time>",output);
		System.out.println(jsonStringAway);
		
		//cancel away that we don't have any active away
		Invocation.Builder invocationBuilderCancelAway = client.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response responseCancelAway = invocationBuilderCancelAway.delete();
		System.out.println(responseCancelAway); 	
		
		//start away
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilderAway = client.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response responseAway = invocationBuilderAway.put(Entity.json(jsonStringAway));
		Assert.assertTrue(responseAway.getStatus() == 200,"Expected status 200. Actual status is :"+ responseAway.getStatus());
		System.out.println(responseAway);
		
		//Assertions start away	ef11
		WaitUtil.tinyWait();	
		Away_DAO_Impl.start_away_ef_11(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_algo_control, t_id);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + Away_DAO_Impl.start_away_thermostat_id_algo_control + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_program_log, t_id);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + Away_DAO_Impl.start_away_thermostat_id_program_log + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_program, user_away_status);
		System.out.println("The progarm status in ef_program is " + Away_DAO_Impl.start_away_thermostat_id_program + ", Expected status is " + user_away_status);
		Assert.assertEquals(Away_DAO_Impl.start_away_user_id_program, u_id);
		System.out.println("The user_id in ef_program is " + Away_DAO_Impl.start_away_user_id_program + ", Expected user_id is " + u_id);	
		
		//Assertions start away	efts
		WaitUtil.tinyWait();
		Away_DAO_Impl.start_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
        
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 2); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 8); // Adding 8 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"away\""),"Expected set_point_reason AWAY");
		//Assert.assertTrue(content1.contains("\"cool_setpoint\":73"),"Expected cool_setpoint 73"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertNotEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertNotEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"away\""),"Expected set_point_reason AWAY");
		//Assert.assertTrue(content2.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT - 532
	public void createSPOStartTimeLessThen5MinAfterScheduleChange(){
		//verify t_stat is in schedule
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertTrue(content.contains("\"cool_setpoint\":72"),"Expected cool_setpoint 72");

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
				
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 4); // Adding 3 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 15); // Adding 14 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
		
        //apply t_stat schedule
        WaitUtil.mediumWait();
        String jsonCoolSetpointChange = APIprop.json_daily_schedule_update_second_time;
        
        SimpleDateFormat dateFormatUTCScheduleUpdate = new SimpleDateFormat("HH:mm");
        dateFormatUTCScheduleUpdate.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendarScheduleUpdate = Calendar.getInstance();
        calendarScheduleUpdate.setTime(new Date()); // Now use today date.
        calendarScheduleUpdate.add(Calendar.MINUTE, 2); // Adding  2 min
        String output = dateFormatUTCScheduleUpdate.format(calendarScheduleUpdate.getTime()); 
		
        jsonCoolSetpointChange = jsonCoolSetpointChange.replaceFirst("<schedule_second_interval_start_time>",output);
		System.out.println(jsonCoolSetpointChange);
        
        
        Invocation.Builder invocationBuilderCoolSetpoint = client.target(dailyScheduleUpdate).request(MediaType.APPLICATION_JSON);
        Response responseCoolSetpoint = invocationBuilderCoolSetpoint.put(Entity.json(jsonCoolSetpointChange));
        WaitUtil.tinyWait();
        Assert.assertTrue(responseCoolSetpoint.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpoint.getStatus());
        System.out.println(responseCoolSetpoint);
        WaitUtil.tinyWait();
        
        Invocation.Builder invocationBuilderState = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
        Response responseStateThermostat = invocationBuilderState.get();
        String contentStateThermostat = responseStateThermostat.readEntity(String.class);
        Assert.assertTrue(responseStateThermostat.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostat.getStatus());
        Assert.assertTrue(contentStateThermostat.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
        Assert.assertTrue(contentStateThermostat.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason Schedule");
        //Assert.assertTrue(contentStateThermostat.contains("\"cool_setpoint\":75"),"Expected cool_setpoint 75");
        System.out.println("Current Thermostat state is " + contentStateThermostat);
        WaitUtil.tinyWait();

        //verify algocontrol for start event is still active
		WaitUtil.tinyWait();
        WaitUtil.threeMinutesWait();
        WaitUtil.threeMinutesWait();
		SPO_DAO_Impl.update_SPO_ef11(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.update_spo_thermostat_id_thermostat_algoritm, t_id);
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":77"),"Expected cool_setpoint 77"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason Schedule");
		Assert.assertTrue(content2.contains("\"cool_setpoint\":75"),"Expected cool_setpoint 75"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
	@Test //PLAT - 490
	public void createSPOScheduleChangeDuringSPOExecution(){
		//verify t_stat is in schedule
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason should be schedule");
		//Assert.assertTrue(content.contains("\"cool_setpoint\":72"),"Expected cool_setpoint 72");

		//apply t_stat schedule
        WaitUtil.mediumWait();
        String jsonCoolSetpointChange = APIprop.json_daily_schedule_update_second_time;
        
        SimpleDateFormat dateFormatUTCScheduleUpdate = new SimpleDateFormat("HH:mm");
        dateFormatUTCScheduleUpdate.setTimeZone(TimeZone.getTimeZone("PST"));
        Calendar calendarScheduleUpdate = Calendar.getInstance();
        calendarScheduleUpdate.setTime(new Date()); // Now use today date.
        calendarScheduleUpdate.add(Calendar.MINUTE, 15); // Adding  2 min
        String output = dateFormatUTCScheduleUpdate.format(calendarScheduleUpdate.getTime()); 
		
        jsonCoolSetpointChange = jsonCoolSetpointChange.replaceFirst("<schedule_second_interval_start_time>",output);
		System.out.println(jsonCoolSetpointChange);
        
        
        Invocation.Builder invocationBuilderCoolSetpoint = client.target(dailyScheduleUpdate).request(MediaType.APPLICATION_JSON);
        Response responseCoolSetpoint = invocationBuilderCoolSetpoint.put(Entity.json(jsonCoolSetpointChange));
        WaitUtil.tinyWait();
        Assert.assertTrue(responseCoolSetpoint.getStatus() == 200,"Expected status 200. Actual status is : "+ responseCoolSetpoint.getStatus());
        System.out.println(responseCoolSetpoint);
        WaitUtil.tinyWait();
        
        Invocation.Builder invocationBuilderState = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
        Response responseStateThermostat = invocationBuilderState.get();
        String contentStateThermostat = responseStateThermostat.readEntity(String.class);
        Assert.assertTrue(responseStateThermostat.getStatus() == 200,"Expected status 200. Actual status is :"+ responseStateThermostat.getStatus());
        Assert.assertTrue(contentStateThermostat.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
        Assert.assertTrue(contentStateThermostat.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason Schedule");
        //Assert.assertTrue(contentStateThermostat.contains("\"cool_setpoint\":75"),"Expected cool_setpoint 75");
        System.out.println("Current Thermostat state is " + contentStateThermostat);
        WaitUtil.tinyWait();
		
		
		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
				
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 7); // Adding 3 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 25); // Adding 14 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
        System.out.println("Inserting SPO start entry into ef 11 DB......");
        //insert start spo entry
		SPO_DAO_Impl.insertStartSpoEntry(t_id, event_ee_start, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		//time conversion for end SPO entry
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		System.out.println("Inserting SPO end entry into ef 11 DB......");
		//insert end spo entry
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);		
		System.out.println("SPO entries were created in db. Waiting to verify SPO starts correctly.......... ");
		
        //verify algocontrol for start event is still active
		WaitUtil.tinyWait();
        WaitUtil.threeMinutesWait();
        WaitUtil.threeMinutesWait();
        WaitUtil.threeMinutesWait();
        WaitUtil.threeMinutesWait();
		SPO_DAO_Impl.update_SPO_ef11(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.update_spo_thermostat_id_thermostat_algoritm, t_id);
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":77"),"Expected cool_setpoint 77"); // 73F = 71F (is latest mo line 95) + 2F (line 31) 
		System.out.println("Current Thermostat state is " + content1);
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_SPO_ef11(t_id, setting_phase_0_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly started. Waiting to verify SPO ends correctly........");
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.hugeWait();
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		Assert.assertTrue(content2.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason Schedule");
		Assert.assertTrue(content2.contains("\"cool_setpoint\":75"),"Expected cool_setpoint 75"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_SPO_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
	
}
