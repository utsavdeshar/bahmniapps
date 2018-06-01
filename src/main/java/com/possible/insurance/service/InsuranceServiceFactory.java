package com.possible.insurance.service;

import org.springframework.stereotype.Service;

@Service
public class InsuranceServiceFactory {

	public InsuranceService getInsuranceServiceImplFactory(int type) {
		if (0 ==type) {
			return new ImisInsuranceServiceImpl();
		} else {
			return new ImisInsuranceServiceImpl();  //TODO: 
		}
	}

}
