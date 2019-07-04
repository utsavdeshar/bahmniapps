package org.bahmni.insurance.service;

import java.util.Map;

import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
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

	public abstract Claim constructFhirClaimRequest(Map<String, Object> claimParams);

	public abstract EligibilityRequest constructFhirEligibilityRequest(String insuranceID);

	public abstract String getFhirPatient(String patientId);

}
