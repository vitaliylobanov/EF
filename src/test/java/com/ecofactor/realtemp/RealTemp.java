package com.ecofactor.realtemp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.dao.Real_Temp_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.JsonUtil;
import com.ecofactorqa.util.PlatDataProp;
import com.ecofactorqa.util.WaitUtil;

public class RealTemp {
	
	private Client client;
	
    //variables for assertions spo
	final int t_id = PlatDataProp.RealTempPlatDigiThermostatId;
	final String str_tid = Integer.toString(t_id);
	
    String next_phase_time_start;
    String date_setup_start;
    String execution_start_time_utc_start;
    
    //state api t_stat change
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", str_tid);
	
	@BeforeClass
	public void init() {

		client = ClientBuilder.newClient();
	}

	
	@Test //PLAT - 721
	public void createRealTempForCoolThermostat_cool_130(){
		
		//cancel all activ real temps for t_stat
		Real_Temp_DAO_Impl.cancel_active_real_temp(t_id);
		
		//verify t_stat is in schedule, plus do mo
		String jsonString = APIprop.json_state_valid_hvac_mode_cool;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseCoolHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseCoolHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseCoolHvacMode.getStatus());
		System.out.println(responseCoolHvacMode);
		
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());

		//PArsing json response
        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reason);
        
        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_mode);
        
        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpoint);
		
        final String temperature_start = knownstate.get("temperature").toString();
        System.out.println("temperature--- " + temperature_start);
		
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 2); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for date_setup.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
 		
        System.out.println("Inserting Real Temp start entry into ef 11 DB......");
        //insert start spo entry
        Real_Temp_DAO_Impl.insertStartRealTempEntry(t_id, next_phase_time_start, date_setup_start, execution_start_time_utc_start);
		
		//verify start setpoint was applied to the t_stat using state api call
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :"+ response1.getStatus());
		System.out.println("Current Thermostat state is " + content1);
		
		//PArsing json response
        final JSONObject jsonObjectend = JsonUtil.parseObject(content1);
        final JSONObject knownstateend = (JSONObject) jsonObjectend.get("best_known_current_state_thermostat_data");
        
        //Printing out State of The t_stat
        final String setpoint_reasonend = jsonObjectend.get("setpoint_reason").toString();
        System.out.println("setpoint_reason--- " + setpoint_reasonend);
        
        final String hvac_modeend = knownstateend.get("hvac_mode").toString();
        System.out.println("hvac_mode--- " + hvac_modeend);
        
        final String cool_setpointend = knownstateend.get("cool_setpoint").toString();
        System.out.println("cool_setpoint--- " + cool_setpointend);
		
        final String temperature_startend = knownstateend.get("temperature").toString();
        System.out.println("temperature--- " + temperature_startend);
		
		//efts start db verification
		WaitUtil.tinyWait();
		WaitUtil.threeMinutesWait();
		Real_Temp_DAO_Impl.start_real_temp_efts(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.start_realtemp_thermostat_id_thermostat_event, t_id);
		
		//ef11 start db verification
		WaitUtil.tinyWait();
		Real_Temp_DAO_Impl.real_temp_rescheduled_ef11(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.reschedule_real_temp_thermostat_id_thermostat_algoritm, t_id);
		
		//verify end setpoint was applied to the t_stat using state api call
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :"+ response2.getStatus());
		System.out.println("Current Thermostat state is " + content2);

	}
	
	@Test //PLAT - 721
	public void createRealTempForHeatThermostat_heat_131(){
		//cancel all activ real temps for t_stat
		Real_Temp_DAO_Impl.cancel_active_real_temp(t_id);
				
		//verify t_stat is in schedule, plus do mo
		String jsonString = APIprop.json_state_valid_hvac_mode_heat;
		Invocation.Builder invocationBuilderHVACMode = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response responseHeatHvacMode = invocationBuilderHVACMode.put(Entity.json(jsonString));
		WaitUtil.tinyWait();	
		Assert.assertTrue(responseHeatHvacMode.getStatus() == 200,"Expected status 200. Actual status is :"+ responseHeatHvacMode.getStatus());
		System.out.println(responseHeatHvacMode);
				
		WaitUtil.tinyWait();
		WaitUtil.tinyWait();
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		String content = response.readEntity(String.class);
		Assert.assertTrue(response.getStatus() == 200,"Expected status 200. Actual status is :"+ response.getStatus());
		Assert.assertTrue(content.contains("\"hvac_mode\":\"heat\""),"Expected hvac_mode HEAT");

		//PArsing json response
		final JSONObject jsonObject = JsonUtil.parseObject(content);
		final JSONObject knownstate = (JSONObject) jsonObject.get("best_known_current_state_thermostat_data");
		       
		//Printing out State of The t_stat
		final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
		System.out.println("setpoint_reason--- " + setpoint_reason);
		        
		final String hvac_mode = knownstate.get("hvac_mode").toString();
		System.out.println("hvac_mode--- " + hvac_mode);
				
		final String temperature_start = knownstate.get("temperature").toString();
		System.out.println("temperature--- " + temperature_start);
				
		// set time for next_phase_time, execution_start_time_utc.
		SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date()); // Now use today date.
		calendar.add(Calendar.MINUTE, 2); // Adding 2 min
		next_phase_time_start = dateFormatUTC.format(calendar.getTime());
		execution_start_time_utc_start = next_phase_time_start;

		// set time for date_setup.
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(new Date()); // Now use today date.
		calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
		date_setup_start = dateFormatUTC.format(calendar2.getTime());

		System.out.println("Inserting Real Temp start entry into ef 11 DB......");
		// insert start spo entry
		Real_Temp_DAO_Impl.insertStartRealTempEntryHeat(t_id,next_phase_time_start, date_setup_start,execution_start_time_utc_start);

		// verify start setpoint was applied to the t_stat using state api call
		WaitUtil.threeMinutesWait();
		Invocation.Builder invocationBuilder1 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response1 = invocationBuilder1.get();
		String content1 = response1.readEntity(String.class);
		Assert.assertTrue(response1.getStatus() == 200,"Expected status 200. Actual status is :" + response1.getStatus());
		System.out.println("Current Thermostat state is " + content1);

		// PArsing json response
		final JSONObject jsonObjectend = JsonUtil.parseObject(content1);
		final JSONObject knownstateend = (JSONObject) jsonObjectend.get("best_known_current_state_thermostat_data");

		// Printing out State of The t_stat
		final String setpoint_reasonend = jsonObjectend.get("setpoint_reason").toString();
		System.out.println("setpoint_reason--- " + setpoint_reasonend);

		final String hvac_modeend = knownstateend.get("hvac_mode").toString();
		System.out.println("hvac_mode--- " + hvac_modeend);

		final String temperature_startend = knownstateend.get("temperature").toString();
		System.out.println("temperature--- " + temperature_startend);

		// efts start db verification
		WaitUtil.tinyWait();
		WaitUtil.threeMinutesWait();
		Real_Temp_DAO_Impl.start_real_temp_efts_heat(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.start_realtemp_thermostat_id_thermostat_event_heat,t_id);

		// ef11 start db verification
		WaitUtil.tinyWait();
		Real_Temp_DAO_Impl.real_temp_rescheduled_ef11_heat(t_id);
		Assert.assertEquals(Real_Temp_DAO_Impl.reschedule_real_temp_thermostat_id_thermostat_algoritm_heat,t_id);

		// verify end setpoint was applied to the t_stat using state api call
		WaitUtil.tinyWait();
		Invocation.Builder invocationBuilder2 = client.target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
		Response response2 = invocationBuilder2.get();
		String content2 = response2.readEntity(String.class);
		Assert.assertTrue(response2.getStatus() == 200,"Expected status 200. Actual status is :" + response2.getStatus());
		System.out.println("Current Thermostat state is " + content2);

	}
	
	
}
