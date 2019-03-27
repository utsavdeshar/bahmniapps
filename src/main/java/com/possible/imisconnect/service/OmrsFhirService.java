package com.possible.imisconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.Properties;
import com.possible.imisconnect.web.RestTemplateFactory;

@Component
public abstract class OmrsFhirService {

	private Properties properties;
	
	private final static int OPENMRS_FHIR_MODULE =1;

	@Autowired
	public OmrsFhirService(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public RestTemplate getApiClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory();
		return restFactory.getRestTemplate(OPENMRS_FHIR_MODULE, properties);
	}

	public abstract String getFhirPatient(String patientId);

}
