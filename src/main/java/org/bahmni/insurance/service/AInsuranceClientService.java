package org.bahmni.insurance.service;

import java.net.URISyntaxException;

import org.bahmni.insurance.Properties;
import org.bahmni.insurance.client.ClientHelperFactory;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityResponse;
import org.hl7.fhir.dstu3.model.Task;
import org.openmrs.module.fhir.api.helper.ClientHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public abstract class AInsuranceClientService {

	private Properties properties;

	private ClientHelper clientHelper;

	@Autowired
	public AInsuranceClientService(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public ClientHelper getClientHelper(String clientType) {
		clientHelper = ClientHelperFactory.createClient(clientType);
		return clientHelper;
	}

	public abstract ClaimResponse getClaimResponse(Claim claimRequest) throws RestClientException, URISyntaxException;

	public abstract EligibilityResponse getElibilityResponse(EligibilityRequest eligbilityRequest);

	public abstract ClaimResponse getClaimStatus(Task claimStatusRequest);

	public abstract ResponseEntity<String> loginCheck();
}
