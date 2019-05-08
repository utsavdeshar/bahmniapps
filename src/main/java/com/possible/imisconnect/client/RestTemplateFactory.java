package com.possible.imisconnect.client;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.ImisConstants;
import com.possible.imisconnect.Properties;

@Component
public class RestTemplateFactory {
	public RestTemplate getRestTemplate(int type, Properties properties) {

		RestTemplate restTemplate = new RestTemplate();
		if (type == ImisConstants.OPENIMIS_FHIR) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.imisUser, properties.imisPassword));
		} else if (type == ImisConstants.OPENMRS_FHIR) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.openmrsUser, properties.openmrsPassword));
		} else if (type == ImisConstants.OPENMRS_ODOO) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.openmrsUser, properties.openmrsPassword));

		}
		return restTemplate;
	}

}