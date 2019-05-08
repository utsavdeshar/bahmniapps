package com.possible.imisconnect.web;

import static org.apache.log4j.Logger.getLogger;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.possible.imisconnect.service.AClientService;
import com.possible.imisconnect.service.AOpernmrsFhirConstructorService;
import com.possible.imisconnect.service.IOpenmrsOdooService;
import com.possible.imisconnect.service.impl.ImisRestClientServiceImpl;
import com.possible.imisconnect.service.impl.OpenmrsFhirConstructorServiceImpl;
import com.possible.imisconnect.service.impl.OpenmrsOdooServiceImpl;

@RestController
public class RequestProcessor {

	private final Logger logger = getLogger(RequestProcessor.class);

	private final AOpernmrsFhirConstructorService fhirConstructorService;
	private final AClientService imisClient;
	private final IOpenmrsOdooService odooService;

	@Autowired
	public RequestProcessor(OpenmrsFhirConstructorServiceImpl fhirConstructorServiceImpl,
			OpenmrsOdooServiceImpl openmrsOdooServiceImpl, ImisRestClientServiceImpl imisRestClientServiceImpl) {
		this.fhirConstructorService = fhirConstructorServiceImpl;
		this.odooService = openmrsOdooServiceImpl;
		this.imisClient = imisRestClientServiceImpl;
	}

	@RequestMapping(path = "/request/eligibity")
	public void requestEligibity(HttpServletResponse response) {
		logger.debug("requestEligibity");

	}

	@RequestMapping(path = "/request/claimsubmit")
	public void requestClaimSubmit(HttpServletResponse response) throws RestClientException, URISyntaxException {
		logger.debug("requestClaimSubmit");
		Claim claimRequest = fhirConstructorService.constructFhirClaimRequest("StringPatientId"); // TODO: get this
																									// StringPatientId
																									// from web param
		ClaimResponse claimResponse = imisClient.getClaimResponse(claimRequest);
	}

	@RequestMapping(path = "/request/claimstatus")
	public void requestClaimStatus(HttpServletResponse response) {
		logger.debug("requestClaimStatus");

	}

	@RequestMapping(path = "/auth")
	public void auth(HttpServletResponse response) {
		logger.debug("Authenticated");

	}

	@RequestMapping(path = "/patient")
	public void generatePatient(HttpServletResponse response) {
		logger.debug("generatePatient");
		System.out.println(fhirConstructorService.getFhirPatient("9065024b-9499-4c9b-9a2f-a53f703be2aa"));

	}

	@RequestMapping(path = "/odercost")
	public void getOrderCost(HttpServletResponse response) {
		logger.debug("getOrderCost");
		System.out.println(odooService.getOrderCost("9065024b-9499-4c9b-9a2f-a53f703be2aa"));

	}

}
