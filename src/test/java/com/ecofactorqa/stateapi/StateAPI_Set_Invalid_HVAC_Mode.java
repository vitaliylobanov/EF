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

public class StateAPI_Set_Invalid_HVAC_Mode {
	
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

	@Test
	public void set_invalid_hvac_mode_empty() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_empty;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_emptyspace() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_emptyspace;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_null() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_null;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_numbers() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_numbers;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_multicommasep() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_multicommasep;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_multitabsep() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_multitabsep;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_noquotes() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_noquotes;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_noquotesempty() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_noquotesempty;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_noquotesnull() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_noquotesnull;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_singlequotes() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_singlequotes;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_nonexisting() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_nonexisting;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
	@Test
	public void set_invalid_hvac_mode_uppercase() {
		String jsonString = APIprop.json_state_invalid_hvac_mode_uppercase;
		Invocation.Builder invocationBuilder = client
				.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		
		System.out.println(response);
	}
	
}