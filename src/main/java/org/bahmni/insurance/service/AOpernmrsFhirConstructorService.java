package org.bahmni.insurance.service;

import java.io.IOException;

import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class AOpernmrsFhirConstructorService {

	@Autowired
	RestTemplateFactory restFactory;
	
	public RestTemplate getApiClient() {
		return restFactory.getRestTemplate(ImisConstants.OPENMRS_FHIR);
	}

	public abstract String constructFhirClaimRequest(String insuranceID)throws IOException;

	public abstract String constructFhirEligibilityRequest(String insuranceID) throws IOException;
	

	public abstract String getFhirPatient(String patientId);

	public abstract String constructFhirClaimTrackRequest(String insuranceID) throws IOException;

}
