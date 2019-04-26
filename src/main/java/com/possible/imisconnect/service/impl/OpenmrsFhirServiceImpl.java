package com.possible.imisconnect.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.possible.imisconnect.Properties;
import com.possible.imisconnect.service.AOpernmrsFhirService;

@Component
@Configurable
public class OpenmrsFhirServiceImpl extends AOpernmrsFhirService {

	public OpenmrsFhirServiceImpl(Properties properties) {
		super(properties);
	}

	@Override
	public String getFhirPatient(String patientId) {
		 HttpHeaders headers = new HttpHeaders();
	     headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     HttpEntity <String> entity = new HttpEntity<String>(headers);
	     return this.getApiClient().exchange(this.getProperties().openmrsUrl+patientId, HttpMethod.GET, entity, String.class).getBody();
	}
	
	
}
