/*
 * ThermostatState_Test.java
 * Copyright (c) 2014, EcoFactor, All Rights Reserved.
 *
 * This software is the confidential and proprietary information of EcoFactor
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with
 * EcoFactor.
 */
package com.ecofactor.qa.automation.consumerapi;

import static com.ecofactor.qa.automation.platform.util.LogUtil.setLogString;

import java.util.List;

import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.ecofactor.common.pojo.ThermostatDataDef.FanMode;
import com.ecofactor.common.pojo.ThermostatDataDef.HvacMode;
import com.ecofactor.common.pojo.analytics.AggDailyStatsThermostatWeather;
import com.ecofactor.common.pojo.timeseries.PartitionedThermostatRangeData;
import com.ecofactor.qa.automation.consumerapi.data.ApiDataProvider;
import com.ecofactor.qa.automation.dao.DaoModule;
import com.ecofactor.qa.automation.dao.ThermostatRangeDataDao;
import com.ecofactor.qa.automation.platform.enums.CustomLogLevel;
import com.ecofactor.qa.automation.util.JsonUtil;
import com.ecofactor.qa.automation.util.UtilModule;
import com.google.inject.Inject;

/**
 * Test class for testing ThermostatState API
 * @author npaila
 */
@Guice(modules = { UtilModule.class, DaoModule.class, ApiModule.class })
public class ThermostatState_Test extends AbstractTest {

    @Inject
    ConsumerApiURL consumerApiURL;
    @Inject
    private ThermostatRangeDataDao thermostatRangeDataDao;

    /**
     * Test_fetching_ Thermostat_Best_Known_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_Thermostat_Best_Known_Data_using_valid_thermostatID(
            final String username, final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatStateSavings(thermostatId,
                securityCookie);

        displayAPIResponse(response, "Thermostat State");

        final String content = response.readEntity(String.class);
        setLogString("Response :'" + response + "'", true);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);

        final JSONObject knownstate = (JSONObject) jsonObject
                .get("best_known_current_state_thermostat_data");

        final String temperature = knownstate.get("temperature").toString();
        System.out.println("temperature---" + temperature);

        final String hold_mode = knownstate.get("hold_mode").toString();
        System.out.println("hold_mode---" + hold_mode);

        final String fan_state = knownstate.get("fan_state").toString();
        System.out.println("fan_state---" + fan_state);

        final String hvac_state = knownstate.get("hvac_state").toString();
        System.out.println("hvac_state---" + hvac_state);

        final String fan_mode = knownstate.get("fan_mode").toString();
        System.out.println("fan_mode---" + fan_mode);

        final String hvac_mode = knownstate.get("hvac_mode").toString();
        System.out.println("hvac_mode---" + fan_state);

        final String cool_setpoint = knownstate.get("cool_setpoint").toString();
        System.out.println("cool_setpoint---" + cool_setpoint);

        final String heat_setpoint = knownstate.get("heat_setpoint").toString();
        System.out.println("heat_setpoint---" + heat_setpoint);

    }

    /**
     * Test_fetching_ last_collected_data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */

    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_last_collected_Data_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatStateSavings(thermostatId,
                securityCookie);

        displayAPIResponse(response, "Thermostat State");

        final String content = response.readEntity(String.class);
        setLogString("Response :'" + response + "'", true);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final JSONObject last_collected = (JSONObject) jsonObject
                .get("last_collected_thermostat_data");

        final String temperature = last_collected.get("temperature").toString();
        System.out.println("l_temperature---" + temperature);

        final String hold_mode = last_collected.get("hold_mode").toString();
        System.out.println("l_hold_mode---" + hold_mode);

        final String fan_state = last_collected.get("fan_state").toString();
        System.out.println("l_fan_state---" + fan_state);

        final String hvac_state = last_collected.get("hvac_state").toString();
        System.out.println("l_hvac_state---" + hvac_state);

        final String fan_mode = last_collected.get("fan_mode").toString();
        System.out.println("l_fan_mode---" + fan_mode);

        final String hvac_mode = last_collected.get("hvac_mode").toString();
        System.out.println("l_hvac_mode---" + hvac_mode);

        final String cool_setpoint = last_collected.get("cool_setpoint").toString();
        System.out.println("l_cool_setpoint---" + cool_setpoint);

        final String heat_setpoint = last_collected.get("heat_setpoint").toString();
        System.out.println("l_heat_setpoint---" + heat_setpoint);

        final String collected = last_collected.get("collected").toString();
        System.out.println("l_collected---" + collected);

        int t_id = Integer.parseInt(thermostatId);
        PartitionedThermostatRangeData stats = thermostatRangeDataDao.findLatestByThermostat(t_id);

        FanMode mode = stats.getFanMode();
        System.out.println("mode++++++++++++++++++++++++++" + mode);

        HvacMode hmode = stats.getHvacMode();
        System.out.println("hvac mode++++++++++++++++++++++++++" + hmode);

        System.out.println("1" + stats.getFanState());
        System.out.println("2" + stats.getConnectionStatus());
        System.out.println("3" + stats.getCoolSetting());
        System.out.println("4" + stats.getHeatSetting());
        System.out.println("5" + stats.getTemperature());
        System.out.println("6" + stats.getId());

    }

    /**
     * Test_fetching_ connection_status_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_Connection_status_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatStateSavings(thermostatId,
                securityCookie);

        displayAPIResponse(response, "Thermostat State");

        final String content = response.readEntity(String.class);
        setLogString("Response :'" + response + "'", true);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final String connected = jsonObject.get("connected").toString();
        Assert.assertTrue(connected.equalsIgnoreCase("true"));
        setLogString(connected, true, CustomLogLevel.MEDIUM);
        setLogString("Connected Status.", true);
    }

    /**
     * Test_fetching_ setpoint_reason_ data_using_valid_thermostat id.
     * @param username the username
     * @param password the password
     * @param thermostatId the thermostat id
     */
    @Test(dataProvider = "hvacSystems", dataProviderClass = ApiDataProvider.class)
    public void test_fetching_SetPoint_reason_using_valid_thermostatID(final String username,
            final String password, final String thermostatId) {

        final Response response = consumerApiURL.getThermostatStateSavings(thermostatId,
                securityCookie);

        displayAPIResponse(response, "Thermostat State");

        final String content = response.readEntity(String.class);
        setLogString("Response :'" + response + "'", true);

        setLogString("Json Response:", true, CustomLogLevel.MEDIUM);
        setLogString(content, true, CustomLogLevel.MEDIUM);

        final JSONObject jsonObject = JsonUtil.parseObject(content);
        final String setpoint_reason = jsonObject.get("setpoint_reason").toString();
        System.out.println("setpoint reason----" + setpoint_reason);
    }
}
