package org.bahmni.insurance.serviceImpl;

import org.bahmni.insurance.model.ClaimResponseModel;
import org.bahmni.insurance.model.ClaimTrackingModel;
import org.bahmni.insurance.model.EligibilityResponseModel;
import org.bahmni.insurance.service.AInsuranceClientService;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.OperationOutcome;
import org.hl7.fhir.dstu3.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ImisFhirClientServiceImpl  {

	/*@Override
	public OperationOutcome submitClaim(Claim claimRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EligibilityResponseModel getElibilityResponse(EligibilityRequest eligbilityRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClaimResponse getClaimStatus(Task claimStatusRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loginCheck() {
		return null;
	}

	@Override
	public ClaimResponseModel getDummyClaimResponse(Claim claimRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EligibilityResponseModel getDummyEligibilityResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClaimTrackingModel getDummyClaimTrack() {
		// TODO Auto-generated method stub
		return null;
	}*/

}
