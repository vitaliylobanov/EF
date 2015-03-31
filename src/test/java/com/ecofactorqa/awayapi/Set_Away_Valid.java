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
	ef11_plat_dbconnection db = new ef11_plat_dbconnection();
	int t_id=32753;
	String user_away_status="USER_AWAY";

	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", "32753");

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	@Test
	public void test_start_Away_using_valid_thermostatID() {
		String jsonString = APIprop.json_away_start_valid_data;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);
		
		WaitUtil.mediumWait();
		
		db.start_away_ef_11(t_id);
		Assert.assertEquals(db.start_away_thermostat_id_algo_control, t_id);
		System.out.println(db.start_away_thermostat_id_algo_control);
		Assert.assertEquals(db.start_away_thermostat_id_program_log, t_id);
		System.out.println(db.start_away_thermostat_id_program_log);
		Assert.assertEquals(db.start_away_thermostat_id_program, user_away_status);
		System.out.println(db.start_away_thermostat_id_program);
		
		WaitUtil.tinyWait();
		
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	
	}
	

	
	
}

