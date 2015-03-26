package com.ecofactorqa.awayapi;




import com.ecofactorqa.util.APIprop;

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

	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", "31437");

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
	
	
	
	}
	

	
	
}

