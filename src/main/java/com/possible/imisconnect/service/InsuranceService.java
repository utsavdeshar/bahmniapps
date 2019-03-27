package com.possible.imisconnect.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public abstract class InsuranceService {

	public InsuranceService() {

	}

	public abstract boolean submitClaims(String claimData, String quotationJsonData);

	public abstract String getClaimStatus(String claimID);

	public abstract String getAvailableBalance(String claimID);

	public abstract ResponseEntity<String> getInsuranceDetails(String claimID);

	public enum Company {
		imis, other
	}

	public abstract ResponseEntity<String> getInsuranceDetails(String claimID, RestTemplate restTemplate);

}
