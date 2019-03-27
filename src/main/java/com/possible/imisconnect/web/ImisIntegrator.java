package com.possible.imisconnect.web;

import static org.apache.log4j.Logger.getLogger;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.possible.imisconnect.service.InsuranceService;
import com.possible.imisconnect.service.InsuranceServiceFactory;

@RestController
public class ImisIntegrator {

	private final Logger logger = getLogger(ImisIntegrator.class);

	private final InsuranceServiceFactory insuranceServiceFactory;

	@Autowired
	public ImisIntegrator(InsuranceServiceFactory insuranceServiceFactory) {
		this.insuranceServiceFactory = insuranceServiceFactory;
	}

	@RequestMapping(path = "/quotation")
	public void sendQuotation(HttpServletResponse response) {
		logger.error("Recieved Quotation");
		InsuranceService imisInsurance = insuranceServiceFactory.getInsuranceServiceImplFactory(0); // TODO:
		imisInsurance.getInsuranceDetails("claimID");

	}

}
