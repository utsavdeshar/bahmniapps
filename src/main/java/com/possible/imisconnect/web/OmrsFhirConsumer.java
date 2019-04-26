package com.possible.imisconnect.web;

import static org.apache.log4j.Logger.getLogger;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.possible.imisconnect.service.AOpernmrsFhirService;
import com.possible.imisconnect.service.IOpenmrsOdooService;
import com.possible.imisconnect.service.impl.OpenmrsFhirServiceImpl;
import com.possible.imisconnect.service.impl.OpenmrsOdooServiceImpl;

@RestController
public class OmrsFhirConsumer {

	private final Logger logger = getLogger(OmrsFhirConsumer.class);

	private final AOpernmrsFhirService fhirService;
	private final IOpenmrsOdooService odooService;


	@Autowired
	public OmrsFhirConsumer(OpenmrsFhirServiceImpl fhirServiceImpl, OpenmrsOdooServiceImpl openmrsOdooServiceImpl) {
		this.fhirService = fhirServiceImpl;
		this.odooService = openmrsOdooServiceImpl;
	}
	
	@RequestMapping(path = "/auth")
	public void auth(HttpServletResponse response) {
		logger.debug("Authenticated");

	}

	@RequestMapping(path = "/patient")
	public void generatePatient(HttpServletResponse response) {
		logger.debug("generatePatient");
		System.out.println(fhirService.getFhirPatient("9065024b-9499-4c9b-9a2f-a53f703be2aa"));

	}
	
	@RequestMapping(path = "/odercost")
	public void getOrderCost(HttpServletResponse response) {
		logger.debug("getOrderCost");
		System.out.println(odooService.getOrderCost("9065024b-9499-4c9b-9a2f-a53f703be2aa"));

	}

}
