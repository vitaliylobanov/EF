package com.ecofactor.realtemp;

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
import com.ecofactorqa.dao.Real_Temp_DAO_Impl;
import com.ecofactorqa.dao.SPO_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.JsonUtil;
import com.ecofactorqa.util.WaitUtil;

public class RealTemp {
	
	private Client client;
	
    //variables for assertions spo
	final int t_id = 32753;
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
	
	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	
	@Test //PLAT - 721
	public void createRealTempForCoolThermostat_cool_130(){
		
		//cancel all activ real temps for t_stat
		Real_Temp_DAO_Impl.cancel_active_real_temp(t_id);
		
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
		
        final String temperature_start = knownstate.get("temperature").toString();
        System.out.println("temperature--- " + temperature_start);
		
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 2); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for date_setup.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
 		
        System.out.println("Inserting Real Temp start entry into ef 11 DB......");
        //insert start spo entry
        Real_Temp_DAO_Impl.insertStartRealTempEntry(t_id, next_phase_time_start, date_setup_start, execution_start_time_utc_start);
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		System.out.println("Current Thermostat state is " + content1);
		
		//PArsing json response
        final JSONObject jsonObjectend = JsonUtil.parseObject(content1);
        final JSONObject knownstateend = (JSONObject) jsonObjectend.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reasonend = jsonObjectend.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reasonend);
        
        final String hvac_modeend = knownstateend.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_modeend);
        
        final String cool_setpointend = knownstateend.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpointend);
		
        final String temperature_startend = knownstateend.get("temperature").toString();
        System.out.println("temperature--- " + temperature_startend);
		
		//efts start db verification
		WaitUtil.tinyWait();
		WaitUtil.threeMinutesWait();
		Real_Temp_DAO_Impl.start_real_temp_efts(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.start_realtemp_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		Real_Temp_DAO_Impl.real_temp_rescheduled_ef11(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.reschedule_real_temp_thermostat_id_thermostat_algoritm, t_id);
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content2.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content2.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason EE");
		//Assert.assertTrue(content2.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		//Assert.assertTrue(content2.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71"); // 71F = 71F (is latest mo line 95)
		System.out.println("Current Thermostat state is " + content2);

	}
	
	@Test //PLAT - 721
	public void createRealTempForHeatThermostat_heat_191(){
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
	
	
}
