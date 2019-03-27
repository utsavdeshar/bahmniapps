package com.possible.imisconnect.web;

import static org.apache.log4j.Logger.getLogger;

import org.apache.log4j.Logger;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.Properties;

@Component
public class RestTemplateFactory {
	private final Logger logger = getLogger(RestTemplateFactory.class);

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