package com.ecofactorqa.awayapi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.ecofactorqa.dao.*;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.WaitUtil;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Set_Away_Valid {
	private Client client;
	
	//variables for assertions
	int t_id=31437;
	String user_away_status="USER_AWAY";
	int u_id=23531;
	int end_away=0;
	final String str_tid = Integer.toString(t_id);
	
    // changing t_id for api call
	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", str_tid);
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", str_tid);

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	@Test
	public void test_start_Away_using_valid_thermostatID_cancel() {
		String jsonString = APIprop.json_away_start_valid_data;
		//cancel away that we dont have any active aways
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1); 
		
		WaitUtil.tinyWait();
		
		//start away
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);
		
		//Assertions start away
		
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
		
		WaitUtil.tinyWait();;
		Away_DAO_Impl.start_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
		
		//cancel away
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder2 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.delete();
		System.out.println(response2);
		
		// Assertions cancel away
		WaitUtil.smallWait();
		Away_DAO_Impl.end_away_ef_11(t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_algo, end_away);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + Away_DAO_Impl.end_away_thermostat_id_algo + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_program_log, end_away);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + Away_DAO_Impl.end_away_thermostat_id_program_log + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_user_id_program, end_away);
		System.out.println("The user_id in ef_program is " + Away_DAO_Impl.end_away_user_id_program + ", Expected thermostat id " + end_away);
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.cancel_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 20 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_70, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 70 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_70 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 10 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10 + ", Expected thermostat id " + t_id);
	
	
	}
	
	
	@Test
	public void test_start_Away_using_valid_thermostatID_cancel_by_mo() {
		String jsonString = APIprop.json_away_start_valid_data;
		//cancel away that we dont have any active aways
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1); 
		
		WaitUtil.tinyWait();
		
		//start away
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);
		
		//Assertions start away
		
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
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.start_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
		
		//cancel away by doing mo
		WaitUtil.tinyWait();
		String moJsonString = APIprop.json_state_valid_coolsetpoint;
		Invocation.Builder invocationBuilder2 = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.put(Entity.json(moJsonString));
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		System.out.println(response2);
		
		// Assertions cancel away
		WaitUtil.smallWait();
		Away_DAO_Impl.end_away_ef_11(t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_algo, end_away);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + Away_DAO_Impl.end_away_thermostat_id_algo + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_program_log, end_away);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + Away_DAO_Impl.end_away_thermostat_id_program_log + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_user_id_program, end_away);
		System.out.println("The user_id in ef_program is " + Away_DAO_Impl.end_away_user_id_program + ", Expected thermostat id " + end_away);
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.cancel_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 20 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 10 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10 + ", Expected thermostat id " + t_id);
	
	
	}

	@Test
	public void test_start_Away_using_valid_thermostatID_natural_end() {
		String jsonString = APIprop.json_away_start_valid_data1;

        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 8); // Adding 8 min
        String output = dateFormatUTC.format(calendar.getTime()); 
		
		jsonString = jsonString.replaceFirst("<end_time>",output);
		System.out.println(jsonString);
		
		
		//cancel away that we don't have any active away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1); 
		
		WaitUtil.tinyWait();
		
		//start away
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);
		
		//Assertions start away
		
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
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.start_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
		

		// Assertions natural end away
		WaitUtil.veryHugeWait();
		WaitUtil.hugeWait();
		
		Away_DAO_Impl.end_away_ef_11(t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_algo, end_away);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + Away_DAO_Impl.end_away_thermostat_id_algo + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_program_log, end_away);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + Away_DAO_Impl.end_away_thermostat_id_program_log + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_user_id_program, end_away);
		System.out.println("The user_id in ef_program is " + Away_DAO_Impl.end_away_user_id_program + ", Expected thermostat id " + end_away);
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.end_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_40, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 40 is " + Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_40 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_60, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 60 is " + Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_60 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_10, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.end_away_thermostat_id_thermostat_event_phase_10 + ", Expected thermostat id " + t_id);
	
	
	}
	
	@Test
	public void test_start_Away_using_valid_thermostatID_updateAway_cancel() {
		String jsonString = APIprop.json_away_start_valid_data1;
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 8); // Adding 8 min
        String output = dateFormatUTC.format(calendar.getTime()); 
		jsonString = jsonString.replaceFirst("<end_time>",output);
		System.out.println(jsonString);
		
		
		//cancel away that we dont have any active aways
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1); 
		
		WaitUtil.tinyWait();
		
		//start away
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);
		
		//Assertions start away
		
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
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.start_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + Away_DAO_Impl.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
		
		//update away
		   //update time
		WaitUtil.tinyWait();
		String jsonStringUpdate = APIprop.json_away_start_valid_data1;
        SimpleDateFormat dateFormatUTCupdate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+'00:00'");
        dateFormatUTCupdate.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendarUpdate = Calendar.getInstance();
        calendarUpdate.setTime(new Date()); // Now use today date.
        calendarUpdate.add(Calendar.MINUTE, 13); // Adding 13 min
        String outputUpdate = dateFormatUTCupdate.format(calendarUpdate.getTime()); 
        jsonStringUpdate = jsonStringUpdate.replaceFirst("<end_time>",outputUpdate);
		System.out.println(jsonStringUpdate);
		   //run api to update time
		Invocation.Builder invocationBuilder2 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.put(Entity.json(jsonStringUpdate));
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		System.out.println(response2);
		
		//cancel away
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder3 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response3 = invocationBuilder3.delete();
		System.out.println(response3);
		
		// Assertions cancel away
		WaitUtil.smallWait();
		Away_DAO_Impl.end_away_ef_11(t_id);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_algo, end_away);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + Away_DAO_Impl.end_away_thermostat_id_algo + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_thermostat_id_program_log, end_away);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + Away_DAO_Impl.end_away_thermostat_id_program_log + ", Expected thermostat id " + end_away);
		Assert.assertEquals(Away_DAO_Impl.end_away_user_id_program, end_away);
		System.out.println("The user_id in ef_program is " + Away_DAO_Impl.end_away_user_id_program + ", Expected thermostat id " + end_away);
		
		WaitUtil.tinyWait();
		Away_DAO_Impl.cancel_away_efts(t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 20 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_20 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_70, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 70 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_70 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 10 is " + Away_DAO_Impl.cancel_away_thermostat_id_thermostat_event_phase_10 + ", Expected thermostat id " + t_id);
	
	
	}
	
}

