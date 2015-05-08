package com.ecofactor.spo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.dao.SPO_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.WaitUtil;

public class SPO {
	
	private Client client;
	
    //variables for assertions
	int t_id = 32753;
	int event_ee_start = 2;
	int setting_phase_0_start = event_ee_start;
	int event_ee_end = 0;
	int setting_phase_0_end = event_ee_end;
	
    String next_phase_time_start;
    String date_setup_start;
    String execution_start_time_utc_start;
    String execution_end_time_utc_start;
    String mo_cutoff_time_utc_start;
    String next_phase_time_end;
    String date_setup_end;
    String execution_start_time_utc_end;
    String mo_cutoff_time_utc_end;
    
    //state api t_stat change
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", "32753");
	
	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	

	@Test
	public void createStartEntriesForSpo(){
		//verify t_stat is in schedule, plus do mo
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertFalse(content.contains("\"setpoint_reason\":\"ee\""),"Expected set_point_reason should not be EE");
		System.out.println("Thermostat is in a cool mode, and set point reason is not ee. ");
		
//		//set t_stat to cool mode
//		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
//		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
//		Response response = invocationBuilderHVACMode.put(Entity.json(jsonString));
//		WaitUtil.tinyWait();	
//		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
//		System.out.println(response);
//
//        //set cool setpoint 
//		String jsonString1 = APIprop.json_state_valid_coolsetpoint;
//		Invocation.Builder invocationBuilderCoolSetpoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
//		Response responseget = invocationBuilderCoolSetpoint.get();
//		String content = responseget.readEntity(String.class);
//		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason SCHEDULE");
//		WaitUtil.smallWait();
//		Response response1 = invocationBuilderCoolSetpoint.put(Entity.json(jsonString1));
//		WaitUtil.tinyWait();
//		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
//		System.out.println(response1);
//		WaitUtil.tinyWait();
//		
//		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
//		Response response2 = invocationBuilder.get();
//		String content1 = response2.readEntity(String.class);
//		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
//		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
//		Assert.assertTrue(content1.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
//		Assert.assertTrue(content1.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
//		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
//		System.out.println(content1);
		
		
		
		
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
		
		//efts sart db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_away_efts(t_id, event_ee_start);
		Assert.assertEquals(SPO_DAO_Impl.start_spo_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.start_away_ef11(t_id, setting_phase_0_start);
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
				
		//efts end db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_away_efts(t_id, event_ee_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_event, t_id);
				
		//ef11 start db verification
		WaitUtil.tinyWait();
		SPO_DAO_Impl.end_away_ef11(t_id, setting_phase_0_end);
		Assert.assertEquals(SPO_DAO_Impl.end_spo_thermostat_id_thermostat_algoritm, t_id);
		System.out.println("SPO was correctly ended. SPO verification is done.");
	}
}
