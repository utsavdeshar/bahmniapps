package com.possible.imisconnect.service;

import static org.apache.log4j.Logger.getLogger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.Properties;
import com.possible.imisconnect.web.RestTemplateFactory;

@Component
@Configurable
public class ImisInsuranceServiceImpl extends InsuranceService {

	private Properties properties;
	
	private final static int OPENIMIS_FHIR =0;

	@Autowired
	public ImisInsuranceServiceImpl(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public RestTemplate getApiClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory();
		return restFactory.getRestTemplate(OPENIMIS_FHIR, properties);
	}

	private final Logger logger = getLogger(ImisInsuranceServiceImpl.class);

	public String getClaimStatus(String claimID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAvailableBalance(String claimID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> getInsuranceDetails(String claimID, RestTemplate restTemplate) {
		logger.debug("restTemplate inside getIsnurance Details");
		return null;

		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setContentType(MediaType.APPLICATION_JSON); JSONObject jsonObject =
		 * new JSONObject(); HttpEntity<String> entity = new
		 * HttpEntity<>(jsonObject.toString(), headers); return
		 * restTemplate.exchange(properties.imisUrl, HttpMethod.POST, entity,
		 * String.class);
		 */ // TODO: set one time properties and restTemplate
	}

	@Override
	public boolean submitClaims(String claimData, String quotationJsonData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ResponseEntity<String> getInsuranceDetails(String claimID) {
		System.out.println("Test getInsuranceDetails ");
		// TODO Auto-generated method stub
		return null;
	}

}
