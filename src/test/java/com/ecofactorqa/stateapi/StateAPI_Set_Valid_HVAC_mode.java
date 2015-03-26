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



public class StateAPI_Set_Valid_HVAC_mode {
	
	private Client client;



	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", "31437");

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
	public void set_thermostat_to_coolsetpoint_fanmode() {
		String jsonString = APIprop.json_state_valid_coolsetpoint_fanmode;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseget = invocationBuilder.get();
		String content = responseget.readEntity(String.class);
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason SCHEDULE");
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (priority = 3)
	public void Get_cool_thermostat_mode_coolsetpoint_fanmode() {
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
		Assert.assertTrue(content.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
	}
		
	@Test (priority = 4)
	public void set_thermostat_to_heat_mode() {
		String jsonString = APIprop.json_state_valid_hvac_mode_heat;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test(priority = 5)
	public void et_thermostat_to_heatsetpoint_fanmode() {
		String jsonString = APIprop.json_state_valid_heatsetpoint_fanmode;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseget = invocationBuilder.get();
		String content = responseget.readEntity(String.class);
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason SCHEDULE");
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test (priority = 6)
	public void Get_heat_thermostat_mode() {
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode HEAT");
		Assert.assertTrue(content.contains("\"heat_setpoint\":64"),"Expected heat_setpoint 64");
		Assert.assertTrue(content.contains("\"fan_mode\":\"on\""),"Expected fan_mode ON");
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
	}
	
}