package com.possible.imisconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.possible.imisconnect.Properties;
import com.possible.imisconnect.service.impl.ImisInsuranceServiceImpl;

@Service
public class FInsuranceServiceFactory {
	private Properties properties;

	@Autowired
	public FInsuranceServiceFactory(Properties properties) {
		this.properties = properties;
	}

	public AInsuranceService getInsuranceServiceImplFactory(int type) {
		if (0 == type) {
			return new ImisInsuranceServiceImpl(properties);
		} else {
			return new ImisInsuranceServiceImpl(properties); // TODO: if further any other insurance is to be integrated
		}
	}

}
