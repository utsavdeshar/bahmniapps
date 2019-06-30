package org.bahmni.insurance.service;

import java.net.URISyntaxException;

import org.bahmni.insurance.client.ClientHelperFactory;
import org.bahmni.insurance.model.ClaimResponseModel;
import org.bahmni.insurance.model.ClaimTrackingModel;
import org.bahmni.insurance.model.EligibilityResponseModel;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityResponse;
import org.hl7.fhir.dstu3.model.Task;
import org.openmrs.module.fhir.api.helper.ClientHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

@Component
public abstract class AInsuranceClientService {
	private ClientHelper clientHelper;

	public ClientHelper getClientHelper(String clientType) {
		clientHelper = ClientHelperFactory.createClient(clientType);
		return clientHelper;
	}

	public abstract ClaimResponse getClaimResponse(Claim claimRequest) throws RestClientException, URISyntaxException;

	public abstract EligibilityResponse getElibilityResponse(EligibilityRequest eligbilityRequest) throws RestClientException, URISyntaxException;

	public abstract ClaimResponse getClaimStatus(Task claimStatusRequest);

	public abstract ResponseEntity<String> loginCheck();

	public abstract ClaimResponseModel getDummyClaimResponse();
	
	public abstract EligibilityResponseModel getDummyEligibilityResponse();
	
	public abstract ClaimTrackingModel getDummyClaimTrack();


}
