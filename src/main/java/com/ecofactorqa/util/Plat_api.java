package com.ecofactorqa.util;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Plat_api {
	
	private Client client;
	private String thermostatStateURL = APIprop.THERMOSTAT_STATE_URL
			.replaceFirst("thermostat_id", "31437");
	
	@Inject
    protected APIprop apiProp;
	
	
	public Response getThermostatstate() {

        client = ClientBuilder.newClient();
        final Invocation.Builder invocationBuilder = client
                .target(thermostatStateURL).request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get();
    }
	
	

}
