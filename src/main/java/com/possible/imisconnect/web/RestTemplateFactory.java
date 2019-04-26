package com.possible.imisconnect.web;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.Properties;

@Component
public class RestTemplateFactory {
	private final static int OPENIMIS = 0; 
	private final static int OPENMRS_FHIR = 1; 
	private final static int OPENMRS_ODOO = 2; 



	public RestTemplate getRestTemplate(int type, Properties properties) {

		RestTemplate restTemplate = new RestTemplate();
		if (type == OPENIMIS) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.imisUser, properties.imisPassword));
		} else if (type == OPENMRS_FHIR) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.openmrsUser, properties.openmrsPassword));
		} else if (type == OPENMRS_ODOO) {
			restTemplate.getInterceptors()
			.add(new BasicAuthorizationInterceptor(properties.openmrsUser, properties.openmrsPassword));

		} 
		return restTemplate;
	}

}