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

import com.ecofactorqa.dao.MO_Detection_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.PlatDataProp;
import com.ecofactorqa.util.WaitUtil;

public class StateAPI_Set_Valid_HVAC_mode {

	private Client client;
	
	//variables for assertions
	int t_id = PlatDataProp.StatePlatDigiThermostatId;
	final String str_tid = Integer.toString(t_id);

	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", str_tid);

	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	@Test(priority = 1)
	public void set_thermostat_to_cool_mode() {
		//set t_stat to cool mode
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);

        //set cool setpoint 
		String jsonString1 = APIprop.json_state_valid_coolsetpoint_fanmode;
		Invocation.Builder invocationBuilderCoolSetpoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		//Response responseget = invocationBuilderCoolSetpoint.get();
		//String content = responseget.readEntity(String.class);
		//Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason SCHEDULE");
		WaitUtil.smallWait();
		Response response1 = invocationBuilderCoolSetpoint.put(Entity.json(jsonString1));
		WaitUtil.tinyWait();
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		System.out.println(response1);
		
		//db verification, efts thermostat event
		WaitUtil.tinyWait();
		//MO_Detection_DAO_Impl.thermostatStateApi_efts_cool_hvac_mode(t_id);
		//Assert.assertEquals(MO_Detection_DAO_Impl.coolHVACMode, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with hvacMode changed to cool " + MO_Detection_DAO_Impl.coolHVACMode + ", Expected thermostat id " + t_id);
		MO_Detection_DAO_Impl.thermostatStateApi_efts_cool_setpoint(t_id);
		Assert.assertEquals(MO_Detection_DAO_Impl.collSetpoint, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with with cool mo event " + MO_Detection_DAO_Impl.collSetpoint + ", Expected thermostat id " + t_id);
		
        //verify T_stat state
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder.get();
		String content1 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"cool\""),"Expected hvac_mode COOL");
		Assert.assertTrue(content1.contains("\"cool_setpoint\":71"),"Expected cool_setpoint 71");
		Assert.assertTrue(content1.contains("\"fan_mode\":\"auto\""),"Expected fan_mode AUTO");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
		System.out.println(content1);
		
//        final JSONObject jsonObject = JsonUtil.parseObject(content1);
//
//        final JSONObject knownstate = (JSONObject) jsonObject
//                .get("best_known_current_state_thermostat_data");
//
//        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
//        System.out.println("cool_setpoint---" + cool_setpoint);
//        
//        final String hvac_mode = knownstate.get("hvac_mode").toString();
//        System.out.println("hvac_mode--- " + hvac_mode);
//
//        final String setpoint_reason = knownstate.get("setpoint_reason").toString();
//        System.out.println("setpoint_reason--- " + setpoint_reason);

		
	}

	@Test(priority = 2)
	public void set_thermostat_to_heat_mode() {
		//set t_stat to heat mode
		String jsonString = APIprop.json_state_valid_hvac_mode_heat;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		System.out.println(response);

		//set heat setpoint 
		String jsonString1 = APIprop.json_state_valid_heatsetpoint_fanmode;
		Invocation.Builder invocationBuilderHeatSetPoint = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseget = invocationBuilderHeatSetPoint.get();
		String content = responseget.readEntity(String.class);
		Assert.assertTrue(content.contains("\"setpoint_reason\":\"schedule\""),"Expected set_point_reason SCHEDULE");
		WaitUtil.smallWait();
		Response response1 = invocationBuilderHeatSetPoint.put(Entity.json(jsonString1));
		WaitUtil.tinyWait();
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"	+ response1.getStatus());
		System.out.println(response1);
		
		//db verification, efts thermostat event
		WaitUtil.tinyWait();
		MO_Detection_DAO_Impl.thermostatStateApi_efts_heat_hvac_mode(t_id);
		//Assert.assertEquals(MO_Detection_DAO_Impl.heatHVACMode, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with hvacMode changed to heat " + MO_Detection_DAO_Impl.heatHVACMode + ", Expected thermostat id " + t_id);
		MO_Detection_DAO_Impl.thermostatStateApi_efts_heat_setpoint(t_id);
		Assert.assertEquals(MO_Detection_DAO_Impl.heatSetpoint, t_id);
		System.out.println("The thermostat_id in ef_thermostat_event with with heat mo event " + MO_Detection_DAO_Impl.heatSetpoint + ", Expected thermostat id " + t_id);

		//verify T_stat state
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder.get();
		String content1 = response2.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"	+ response2.getStatus());
		Assert.assertTrue(content1.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode HEAT");
		Assert.assertTrue(content1.contains("\"heat_setpoint\":64"),"Expected heat_setpoint 64");
		Assert.assertTrue(content1.contains("\"fan_mode\":\"on\""),"Expected fan_mode ON");
		Assert.assertTrue(content1.contains("\"setpoint_reason\":\"mo\""),"Expected set_point_reason MO");
	}

}