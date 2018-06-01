package com.possible.insurance.web;

import static org.apache.log4j.Logger.getLogger;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.possible.insurance.Properties;
import com.possible.insurance.service.InsuranceService;
import com.possible.insurance.service.InsuranceServiceFactory;

@RestController
public class InsuranceIntegrator {
	
	
	private final Logger logger = getLogger(InsuranceIntegrator.class);
	
	private final Properties properties;
	
	@Autowired
	public InsuranceIntegrator(Properties properties) {
		this.properties =  properties;
	}

	/*@RequestMapping(path = "/quotation")
	public void sendQuotation(@RequestParam("quotation") String quotationJsonData, @RequestParam("claimId") String claimId, @RequestParam("id") int companyId,  HttpServletResponse response) {
		logger.error("Recieved Quotation");
		InsuranceServiceFactory insrServFactory =  new InsuranceServiceFactory();
		InsuranceService imisService = insrServFactory.getInsuranceServiceImplFactory(companyId); //TODO:
		
		//RestTemplate restClient = restTemplateFactory.getRestTemplate(companyId);
		
		imisService.getInsuranceDetails(claimId);
		imisService.getAvailableBalance(claimId);
		imisService.submitClaims(claimId, quotationJsonData);
	}*/
	
	@RequestMapping(path = "/quotation")
	public void sendQuotation( HttpServletResponse response) {
		logger.error("Recieved Quotation");
		
		InsuranceServiceFactory insrServFactory =  new InsuranceServiceFactory();
		InsuranceService insuranceService = insrServFactory.getInsuranceServiceImplFactory(0); //TODO:
		
		RestTemplateFactory restFactory = new RestTemplateFactory();
		RestTemplate restTemplate  = restFactory.getRestTemplate(0, properties);
		
		insuranceService.getInsuranceDetails("ClaimId", restTemplate);
	}

}
