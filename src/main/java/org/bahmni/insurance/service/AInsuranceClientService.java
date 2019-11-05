package org.bahmni.insurance.service;

import java.net.URISyntaxException;

import org.bahmni.insurance.model.ClaimResponseModel;
import org.bahmni.insurance.model.EligibilityResponseModel;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.Task;
/*import org.openmrs.module.fhir.api.helper.ClientHelper;*/
import org.springframework.web.client.RestClientException;

public abstract class AInsuranceClientService {
	/*private ClientHelper clientHelper;

	public ClientHelper getClientHelper(String clientType) {
		clientHelper = ClientHelperFactory.createClient(clientType);
		return clientHelper;
	}
*/
	public abstract ClaimResponseModel submitClaim(Claim claimRequest) throws RestClientException, URISyntaxException;

	public abstract ClaimResponseModel getClaimResponse(String claimID);
	
	public abstract EligibilityResponseModel checkEligibility(EligibilityRequest eligibilityRequest) throws RestClientException, URISyntaxException;
	
	public abstract ClaimResponse getClaimStatus(Task claimStatusRequest);

	public abstract String loginCheck();


}
