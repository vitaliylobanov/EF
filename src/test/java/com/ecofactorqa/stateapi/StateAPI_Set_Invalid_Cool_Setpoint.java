package com.ecofactorqa.stateapi;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.PlatDataProp;

public class StateAPI_Set_Invalid_Cool_Setpoint {
	
	private Client client;


	int t_id = PlatDataProp.StatePlatDigiThermostatId;
	final String str_tid = Integer.toString(t_id);
	
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", str_tid);

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}
	
	@Test (priority = 1)
	public void set_thermostat_to_cool_mode() {
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (priority = 2)
	public void Get_thermostat_mode() {
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
	}

	@Test (dependsOnMethods = {"Get_thermostat_mode"}, priority = 3)
	public void set_invalid_coolsetpoint_empty() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_empty;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (dependsOnMethods = {"Get_thermostat_mode"}, priority = 4)
	public void set_invalid_coolsetpoint_zero() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_zero;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 5)
	public void set_invalid_coolsetpoint_null() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_null;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (dependsOnMethods = {"Get_thermostat_mode"}, priority = 6)
	public void set_invalid_coolsetpoint_boundarylow() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_boundarylow;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (dependsOnMethods = {"Get_thermostat_mode"}, priority = 7)
	public void set_invalid_coolsetpoint_boundaryhigh() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_boundaryhigh;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 8)
	public void set_invalid_coolsetpoint_decimalcoma() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_decimalcomma;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 9)
	public void set_invalid_coolsetpoint_decimaldot() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_decimaldot;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 10)
	public void set_invalid_coolsetpoint_doublequotes() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_doublequotes;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 11)
	public void set_invalid_coolsetpoint_singlequotes() {
		String jsonString = APIprop.json_state_invalid_coolsetpoint_singlequotes;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(dependsOnMethods = {"Get_thermostat_mode"}, priority = 12)
	public void set_valid_heatsetpoint() {
		String jsonString = APIprop.json_state_valid_heatsetpoint;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
}