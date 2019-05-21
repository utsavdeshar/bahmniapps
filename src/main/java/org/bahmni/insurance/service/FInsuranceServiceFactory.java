package org.bahmni.insurance.service;

import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.Properties;
import org.bahmni.insurance.serviceImpl.ImisRestClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
