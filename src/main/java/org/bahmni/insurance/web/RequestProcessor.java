package org.bahmni.insurance.web;

import static org.apache.log4j.Logger.getLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.bahmni.insurance.dao.FhirResourceDaoServiceImpl;
import org.bahmni.insurance.dao.IFhirResourceDaoService;
import org.bahmni.insurance.model.ClaimResponseModel;
import org.bahmni.insurance.model.FhirResourceModel;
import org.bahmni.insurance.service.AOpernmrsFhirConstructorService;
import org.bahmni.insurance.service.FInsuranceServiceFactory;
import org.bahmni.insurance.service.IOpenmrsOdooService;
import org.bahmni.insurance.serviceImpl.OpenmrsFhirConstructorServiceImpl;
import org.bahmni.insurance.serviceImpl.OpenmrsOdooServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
public class RequestProcessor {

	private final Logger logger = getLogger(RequestProcessor.class);

	private final AOpernmrsFhirConstructorService fhirConstructorService;
	private final IOpenmrsOdooService odooService;
	private final IFhirResourceDaoService fhirDaoService;
	private final FInsuranceServiceFactory insuranceImplFactory;
	// private final RestTemplateFactory restFactory;

	private final AppProperties properties;

	@Autowired
	public RequestProcessor(OpenmrsFhirConstructorServiceImpl fhirConstructorServiceImpl,
			OpenmrsOdooServiceImpl openmrsOdooServiceImpl, FhirResourceDaoServiceImpl fhirServiceImpl,
			FInsuranceServiceFactory insuranceImplFactory, RestTemplateFactory restFactory, AppProperties props) {
		this.fhirConstructorService = fhirConstructorServiceImpl;
		this.odooService = openmrsOdooServiceImpl;
		this.fhirDaoService = fhirServiceImpl;
		this.insuranceImplFactory = insuranceImplFactory;
		this.properties = props;
		// this.restFactory = restFactory;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/request/eligibility/{patientId}", produces = "application/json")
	@ResponseBody
	public String requestEligibity(HttpServletResponse response, @PathVariable("patientId") String patientId)
			throws IOException {
		logger.debug("requestEligibity");

		InputStream is = RequestProcessor.class.getResourceAsStream("/FHIR-Resources/eligibility-response.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String content;
		while ((content = reader.readLine()) != null) {
			System.out.println(content);
		}
		logger.debug(content);

		return content;

	}

	@RequestMapping(method = RequestMethod.GET, value = "/get/claimResponse/{claimId}", produces = "application/json")
	@ResponseBody
	public String getClaimResponse(HttpServletResponse response, @PathVariable("claimId") String patientId)
			throws IOException {
		logger.debug("claimResponse");
		ClaimResponseModel claimResponse = insuranceImplFactory.getInsuranceServiceImpl(100, properties)
				.getDummyClaimResponse();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.setPrettyPrinting().create();
		logger.debug("ClaimResponse model == " + gson.toJson(claimResponse));
		return gson.toJson(claimResponse);

	}

	@RequestMapping(path = "/openIMIS/login")
	@ResponseBody
	public ResponseEntity<String> checkLogin(HttpServletResponse response)
			throws RestClientException, URISyntaxException {
		logger.debug("requestEligibity");
		return insuranceImplFactory.getInsuranceServiceImpl(0, properties).loginCheck();// TODO: remove hardcoded
	}

	@RequestMapping(path = "/request/claimsubmit")
	public void requestClaimSubmit(HttpServletResponse response) throws RestClientException, URISyntaxException {
		logger.debug("requestClaimSubmit");
		/*
		 * Claim claimRequest =
		 * fhirConstructorService.constructFhirClaimRequest("StringPatientId"); // TODO:
		 * get this // StringPatientId ClaimResponse claimResponse =
		 * insuranceImplFactory.getInsuranceServiceImpl(0, properties)
		 * .getClaimResponse(claimRequest); // TODO: remove hardcoded
		 */ }

	@RequestMapping(path = "/get/fhir/claims")
	@ResponseBody
	public List<FhirResourceModel> getFhirClaim() {
		return fhirDaoService.findAll();

	}

	@RequestMapping(path = "/add/fhir/claim")
	@ResponseBody
	public int addFhirClaim() {
		return fhirDaoService.insertFhirResource();

	}

	@RequestMapping(path = "/get/fhir/claim/id")
	@ResponseBody
	public List<String> getFhirClaimId() {
		return fhirDaoService.getClaimId();

	}

	@RequestMapping(path = "/request/claimstatus")
	public void requestClaimStatus(HttpServletResponse response) {
		logger.debug("requestClaimStatus");

	}

	@RequestMapping(path = "/auth")
	public void auth(HttpServletResponse response) {
		logger.debug("Authenticated");
	}

	@RequestMapping(method = RequestMethod.GET, value = "/patient/{patientId}", produces = "application/json")
	@ResponseBody
	public String generatePatient(HttpServletResponse response, @PathVariable("patientId") String patientId) {
		logger.debug("generatePatient");
		return fhirConstructorService.getFhirPatient(patientId); // 9065024b-9499-4c9b-9a2f-a53f703be2aa

	}

	@RequestMapping(path = "/odercost")
	public void getOrderCost(HttpServletResponse response) {
		logger.debug("getOrderCost");
		System.out.println(odooService.getOrderCost("9065024b-9499-4c9b-9a2f-a53f703be2aa"));

	}

}
