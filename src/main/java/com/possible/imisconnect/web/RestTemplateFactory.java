package com.possible.imisconnect.web;

import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.Properties;

@Component
public class RestTemplateFactory {

	public RestTemplate getRestTemplate(int type, Properties properties) {

		RestTemplate restTemplate = new RestTemplate();
		if (type == 0) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.imisUser, properties.imisPassword));
		} else if (type == 1) {
			restTemplate.getInterceptors()
					.add(new BasicAuthorizationInterceptor(properties.openmrsUser, properties.openmrsPassword));
		}
		return restTemplate;
	}

}