package com.possible.imisconnect.service.impl;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.possible.imisconnect.ImisConstants;
import com.possible.imisconnect.Properties;
import com.possible.imisconnect.client.RestTemplateFactory;
import com.possible.imisconnect.service.IOpenmrsOdooService;

@Component
@Configurable
public class OpenmrsOdooServiceImpl implements IOpenmrsOdooService {

	private Properties properties;

	@Autowired
	public OpenmrsOdooServiceImpl(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public RestTemplate getApiClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory();
		return restFactory.getRestTemplate(ImisConstants.OPENMRS_ODOO, properties);
	}

	@Override
	public String getOrderCost(String patientId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return this.getApiClient()
				.exchange(this.getProperties().openmrsOdooApi + patientId, HttpMethod.GET, entity, String.class)
				.getBody();
	}

}
