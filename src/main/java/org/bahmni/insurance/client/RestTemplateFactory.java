package org.bahmni.insurance.client;

import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.Properties;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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