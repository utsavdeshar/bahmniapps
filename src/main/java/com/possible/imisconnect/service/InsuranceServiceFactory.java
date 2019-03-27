package com.possible.imisconnect.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.possible.imisconnect.Properties;

@Service
public class InsuranceServiceFactory {
	private Properties properties;

	@Autowired
	public InsuranceServiceFactory(Properties properties) {
		this.properties = properties;
	}

	public InsuranceService getInsuranceServiceImplFactory(int type) {
		if (0 == type) {
			return new ImisInsuranceServiceImpl(properties);
		} else {
			return new ImisInsuranceServiceImpl(properties); // TODO:
		}
	}

}
