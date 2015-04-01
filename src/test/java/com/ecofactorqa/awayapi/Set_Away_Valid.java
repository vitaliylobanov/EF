package com.ecofactorqa.awayapi;

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
	Away_DAO_Impl db = new Away_DAO_Impl();
	
	//variables for assertions
	int t_id=32753;
	String user_away_status="USER_AWAY";
	int u_id=31861;
	
    // changing t_id for api call
	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", "32753");

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	@Test
	public void test_start_Away_using_valid_thermostatID() {
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
		
		WaitUtil.tinyWait();
		
		db.start_away_ef_11(t_id);
		Assert.assertEquals(db.start_away_thermostat_id_algo_control, t_id);
		System.out.println("The thermostat_id in ef_thermostat_algo_control is " + db.start_away_thermostat_id_algo_control + ", Expected thermostat id " + t_id);
		Assert.assertEquals(db.start_away_thermostat_id_program_log, t_id);
		System.out.println("The thermostat_id in ef_thermostat_program_log is " + db.start_away_thermostat_id_program_log + ", Expected thermostat id " + t_id);
		Assert.assertEquals(db.start_away_thermostat_id_program, user_away_status);
		System.out.println("The progarm status in ef_program is " + db.start_away_thermostat_id_program + ", Expected status is " + user_away_status);
		Assert.assertEquals(db.start_away_user_id_program, u_id);
		System.out.println("The user_id in ef_program is " + db.start_away_user_id_program + ", Expected user_id is " + u_id);
		
		WaitUtil.tinyWait();
		db.start_away_efts(t_id);
		Assert.assertEquals(db.start_away_thermostat_id_thermostat_event_phase_50, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 50 is " + db.start_away_thermostat_id_thermostat_event_phase_50 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(db.start_away_thermostat_id_thermostat_event_phase_30, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 30 is " + db.start_away_thermostat_id_thermostat_event_phase_30 + ", Expected thermostat id " + t_id);
		Assert.assertEquals(db.start_away_thermostat_id_thermostat_event_phase_0, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with phase 0 is " + db.start_away_thermostat_id_thermostat_event_phase_0 + ", Expected thermostat id " + t_id);
		
		
		WaitUtil.tinyWait();
		//cancel away
		Invocation.Builder invocationBuilder2 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.delete();
		System.out.println(response2);
	
	
	}
	

	
	
}

