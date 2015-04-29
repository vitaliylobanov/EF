package com.ecofactor.spo;

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

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.ecofactorqa.dao.Away_DAO_Impl;
import com.ecofactorqa.dao.SPO_DAO_Impl;
import com.ecofactorqa.util.APIprop;
import com.ecofactorqa.util.WaitUtil;

public class SPO {
    //variables for assertions
	int t_id=32753;
    String next_phase_time_start;
    String date_setup_start;
    String execution_start_time_utc_start;
    String execution_end_time_utc_start;
    String mo_cutoff_time_utc_start;
    String next_phase_time_end;
    String date_setup_end;
    String execution_start_time_utc_end;
    String mo_cutoff_time_utc_end;
	

	@Test
	public void createStartEntriesForSpo(){
		//set time for next_phase_time, execution_start_time_utc.
        SimpleDateFormat dateFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Now use today date.
        calendar.add(Calendar.MINUTE, 3); // Adding 2 min
        next_phase_time_start = dateFormatUTC.format(calendar.getTime());
        execution_start_time_utc_start = next_phase_time_start;
        
		//set time for execution_end_time_utc.
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date()); // Now use today date.
        calendar1.add(Calendar.MINUTE, 6); // Adding 2 min
        execution_end_time_utc_start = dateFormatUTC.format(calendar1.getTime());
        
		//set time for date_setup,mo_cutoff_time_utc.
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date()); // Now use today date.
        calendar2.add(Calendar.MINUTE, 1); // Adding 1 min
        date_setup_start = dateFormatUTC.format(calendar2.getTime());
        mo_cutoff_time_utc_start = date_setup_start;
 		
		SPO_DAO_Impl.insertStartSpoEntry(t_id, next_phase_time_start, date_setup_start, execution_start_time_utc_start, execution_end_time_utc_start, mo_cutoff_time_utc_start);
		
		next_phase_time_end = execution_end_time_utc_start;
		date_setup_end = date_setup_start;
		execution_start_time_utc_end = execution_end_time_utc_start;
		mo_cutoff_time_utc_end = mo_cutoff_time_utc_start;
		
		SPO_DAO_Impl.insertEndSpoEntry(t_id, next_phase_time_end, date_setup_end, execution_start_time_utc_end, mo_cutoff_time_utc_end);
		
		
	}
}
