package com.ecofactorqa.stateapi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.dao.Thermostat_State_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.JsonUtil;
import com.ecofactorqa.util.WaitUtil;

public class test {

	private Client client;
	
	//variables for assertions
	int t_id = 31437;
	int daily_schedule_id = 259955;
	final String str_tid = Integer.toString(t_id);
	final String str_schedule_id = Integer.toString(daily_schedule_id);
	

	private String thermostatStateURL = APIprop.SET_DAILY_SCHEDULE_UPDATE_URL
			.replaceFirst("thermostat_id", str_tid).replaceFirst("daily_schedule_id",str_schedule_id);

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	@Test(priority = 1)
	public void set_thermostat_to_cool_mode() {
		//set t_stat to cool mode
		String jsonString = APIprop.json_daily_schedule_update;
//		String jsonString1 = APIprop.json_state_valid_coolsetpointSPO;
//		String jsonString2 = APIprop.json_state_valid_coolsetpoint;
//		String jsonString3 = APIprop.json_state_valid_coolsetpointSPO;
		
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);		
//		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
//		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);		
//		Invocation.Builder invocationBuilder3 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		
		Response response = invocationBuilder.put(Entity.json(jsonString));	
//		Response response1 = invocationBuilder1.put(Entity.json(jsonString1));
//		Response response2 = invocationBuilder2.put(Entity.json(jsonString2));	
//		Response response3 = invocationBuilder3.put(Entity.json(jsonString3));
	
		System.out.println(response);
//		System.out.println(response1);
//		System.out.println(response2);
//		System.out.println(response3);
	
		
	}
}