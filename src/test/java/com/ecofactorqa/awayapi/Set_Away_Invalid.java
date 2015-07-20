package com.ecofactorqa.awayapi;

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
import com.ecofactorqa.dao.*;

public class Set_Away_Invalid {

	Away_DAO_Impl db = new Away_DAO_Impl();
	
	private Client client;
	int t_id=32753;
	final String str_tid = Integer.toString(t_id);

	private String setThermostatAway = APIprop.SET_THERMOSTAT_AWAY_URL
			.replaceFirst("thermostat_id", str_tid);

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();

		
	
	}
	
	@Test
	public void test_start_Away_using_past_date() {
		String jsonString = APIprop.json_away_start_invalid_date_past;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("End time is in the past"),"End time is in the past");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
		

		
		

	}
	
	@Test
	public void test_start_Away_using_no_date() {
		String jsonString = APIprop.json_away_start_invalid_date_no_date;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
		
	
	}
	
	@Test
	public void test_start_Away_using_no_end_time() {
		String jsonString = APIprop.json_away_start_invalid_date_no_time;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_hours() {
		String jsonString = APIprop.json_away_start_invalid_date_no_hours;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_minutes() {
		String jsonString = APIprop.json_away_start_invalid_date_no_minutes;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_sec() {
		String jsonString = APIprop.json_away_start_invalid_date_no_seconds;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_neg_hours() {
		String jsonString = APIprop.json_away_start_invalid_date_negative_hours;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_neg_min() {
		String jsonString = APIprop.json_away_start_invalid_date_negative_min;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_neg_sec() {
		String jsonString = APIprop.json_away_start_invalid_date_negative_sec;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_T() {
		String jsonString = APIprop.json_away_start_invalid_date_no_cap_T;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_noT_S() {
		String jsonString = APIprop.json_away_start_invalid_date_no_cap_T_S;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_noT_space() {
		String jsonString = APIprop.json_away_start_invalid_date_space_date_time;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_two_digit_year() {
		String jsonString = APIprop.json_away_start_invalid_date_two_digit_year;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_one_digit_month() {
		String jsonString = APIprop.json_away_start_invalid_date_one_digit_month;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_one_digit_day() {
		String jsonString = APIprop.json_away_start_invalid_date_one_digit_day;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_zero_year() {
		String jsonString = APIprop.json_away_start_invalid_date_zero_year;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_zero_month() {
		String jsonString = APIprop.json_away_start_invalid_date_zero_month;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_zero_day() {
		String jsonString = APIprop.json_away_start_invalid_date_zero_day;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_year() {
		String jsonString = APIprop.json_away_start_invalid_date_no_year;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_month() {
		String jsonString = APIprop.json_away_start_invalid_date_no_month;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_no_day() {
		String jsonString = APIprop.json_away_start_invalid_date_no_day;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_one_digit_min() {
		String jsonString = APIprop.json_away_start_invalid_date_one_digit_min;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_one_digit_sec() {
		String jsonString = APIprop.json_away_start_invalid_date_one_digit_sec;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_incorrect_time_format_semicolons() {
		String jsonString = APIprop.json_away_start_invalid_date_incorrect_time_format_semicolons;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_incorrect_time_format_dash() {
		String jsonString = APIprop.json_away_start_invalid_date_incorrect_time_format_dash;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_incorrect_time_format_dot() {
		String jsonString = APIprop.json_away_start_invalid_date_incorrect_time_format_dot;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Invalid format for end time"),"Invalid format for end time");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_empty() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_empty;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_zero() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_zero;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_null() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_null;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Missing required attribute"),"Missing required attribute");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_one() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_one;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_negative() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_negative;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_five_digit() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_five_digit;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_coma() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_coma;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_no_coma_end() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_no_coma_at_the_end;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_boundary_max() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_boundary_max;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_boundary_min() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_boundary_min;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_cool_setpoint_char() {
		String jsonString = APIprop.json_away_start_invalid_cool_setpoint_char;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_empty() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_empty;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_zero() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_zero;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_null() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_null;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 400,"Expected status 400. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Missing required attribute"),"Missing required attribute");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
		
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_one() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_one;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_negative() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_negative;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_five_digit() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_five_digit;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_coma() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_coma;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_no_coma_end() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_no_coma_at_the_end;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_boundary_max() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_boundary_max;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_boundary_min() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_boundary_min;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		String content = response.readEntity(String.class);
		Assert.assertTrue(content.contains("Setpoint out of range. Valid [45-89]"),"Setpoint out of range. Valid [45-89]");
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	@Test
	public void test_start_Away_using_invalid_heat_setpoint_char() {
		String jsonString = APIprop.json_away_start_invalid_heat_setpoint_char;
		Invocation.Builder invocationBuilder = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.put(Entity.json(jsonString));
		Assert.assertTrue(response.getStatus() == 500,"Expected status 500. Actual status is :"+ response.getStatus());
		System.out.println(response);
		//cancel away
		Invocation.Builder invocationBuilder1 = client
				.target(setThermostatAway).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.delete();
		System.out.println(response1);
	
	}
	
	
	
}
