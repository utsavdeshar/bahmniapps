package com.possible.imisconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.possible.imisconnect.ImisConstants;
import com.possible.imisconnect.Properties;
import com.possible.imisconnect.service.impl.ImisRestClientServiceImpl;

@Service
public class FInsuranceServiceFactory {
	private Properties properties;

	@Autowired
	public FInsuranceServiceFactory(Properties properties) {
		this.properties = properties;
	}

	public AInsuranceClientService getInsuranceServiceImplFactory(int type) {
		if (ImisConstants.OPENIMIS_FHIR == type) {
			return new ImisRestClientServiceImpl(properties);
		} else {
			return new ImisRestClientServiceImpl(properties); // TODO: if further any other insurance is to be integrated
		}
	}

}
