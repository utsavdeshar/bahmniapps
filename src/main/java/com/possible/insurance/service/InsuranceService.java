package com.possible.insurance.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.insurance.Properties;
import com.possible.insurance.web.RestTemplateFactory;

@Component
public abstract class InsuranceService{
	
	public abstract String getApiClient(int type);
	
	public abstract boolean submitClaims(String claimData, String quotationJsonData);
	
	public abstract String getClaimStatus(String claimID);
	
	public abstract String getAvailableBalance(String claimID);
	
	public abstract ResponseEntity<String> getInsuranceDetails(String claimID);

	public enum company {
		imis,
		other
	}
	
	public RestTemplate getApiClient(int type, Properties properties) {
		RestTemplateFactory restFactory = new RestTemplateFactory();
		return restFactory.getRestTemplate(type, properties);
	}

	public abstract ResponseEntity<String> getInsuranceDetails(String claimID, RestTemplate restTemplate);
	
}
