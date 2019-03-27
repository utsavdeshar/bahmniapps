package com.possible.imisconnect.web;

import static org.apache.log4j.Logger.getLogger;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.possible.imisconnect.Properties;
import com.possible.imisconnect.service.OmrsFhirService;
import com.possible.imisconnect.service.OmrsFhirServiceImpl;

@RestController
public class OmrsFhirConsumer {
	
	
	private final Logger logger = getLogger(OmrsFhirConsumer.class);
	
	private final Properties properties;
	
	private final OmrsFhirService fhirService;

	
	@Autowired
	public OmrsFhirConsumer(Properties properties,  OmrsFhirServiceImpl fhirServiceImpl) {
		this.properties =  properties;
		this.fhirService = fhirServiceImpl;
	}	
	
	@RequestMapping(path = "/patient")
	public void generatePatient(HttpServletResponse response) {
		logger.error("patient");
		System.out.println(fhirService.getFhirPatient("0b862bca-e780-4b7e-807e-1df392826fbd"));
		
	}

}
