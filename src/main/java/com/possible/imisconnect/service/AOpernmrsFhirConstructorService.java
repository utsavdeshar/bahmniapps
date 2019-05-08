package com.possible.imisconnect.service;

import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.ImisConstants;
import com.possible.imisconnect.Properties;
import com.possible.imisconnect.client.RestTemplateFactory;

@Component
public abstract class AOpernmrsFhirConstructorService {

	private Properties properties;

	@Autowired
	public AOpernmrsFhirConstructorService(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public RestTemplate getApiClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory();
		return restFactory.getRestTemplate(ImisConstants.OPENMRS_FHIR, properties);
	}

	public abstract Claim constructFhirClaimRequest(String patientId);

	public abstract EligibilityRequest constructFhirEligibilityRequest(String patientId);

	public abstract String getFhirPatient(String patientId);

}
