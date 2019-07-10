package org.bahmni.insurance.service;

import java.io.IOException;
import java.util.Map;

import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

public abstract class AOpernmrsFhirConstructorService {

	@Autowired
	RestTemplateFactory restFactory;

	public RestTemplate getApiClient() {
		return restFactory.getRestTemplate(ImisConstants.OPENMRS_FHIR);
	}

	public abstract Claim constructFhirClaimRequest(Map<String, Object> claimParams) throws IOException;

	public abstract EligibilityRequest constructFhirEligibilityRequest(String insuranceID) throws IOException;

	public abstract ResponseEntity<String> getFhirPatient(String patientId);

	public abstract Task constructFhirClaimTrackRequest(String insuranceID) throws IOException;

	public abstract ResponseEntity<String> createFhirPatient(String patientId);

}
