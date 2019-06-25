package org.bahmni.insurance.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.spi.LoggerFactory;
import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.bahmni.insurance.dao.FhirResourceDaoServiceImpl;
import org.bahmni.insurance.model.EligibilityBalance;
import org.bahmni.insurance.model.EligibilityResponseModel;
import org.bahmni.insurance.model.Insurance;
import org.bahmni.insurance.service.FInsuranceServiceFactory;
import org.bahmni.insurance.serviceImpl.OpenmrsFhirConstructorServiceImpl;
import org.bahmni.insurance.serviceImpl.OpenmrsOdooServiceImpl;
import org.hl7.fhir.dstu3.model.EligibilityResponse.InsuranceComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class InsuranceController {

	private final FInsuranceServiceFactory insuranceImplFactory;
	private final AppProperties properties;
	   @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    }

	@Autowired
	public InsuranceController(OpenmrsFhirConstructorServiceImpl fhirConstructorServiceImpl,
			OpenmrsOdooServiceImpl openmrsOdooServiceImpl, FhirResourceDaoServiceImpl fhirServiceImpl,
			FInsuranceServiceFactory insuranceImplFactory, RestTemplateFactory restFactory, AppProperties props) {
		this.insuranceImplFactory = insuranceImplFactory;
		this.properties = props;

		// this.restFactory = restFactory;
	}
    
	@RequestMapping(value="/add-info", method=RequestMethod.GET)
	public String addInfo(Insurance insurance) {
	return "add-info";
	}
	
	@RequestMapping(value="/add-info", method=RequestMethod.POST)
	public String showWelcomePage(@ModelAttribute @Valid Insurance insurance, BindingResult bindingResult, Model model, @RequestParam String nhisNumber,
			@RequestParam Boolean isMember) throws IOException {
		 if (bindingResult.hasErrors()) {
	            System.out.println("BINDING RESULT ERROR");
	            return "index";
	        } else {
		EligibilityResponseModel eligibilityResponse = insuranceImplFactory.getInsuranceServiceImpl(100, properties)
				.getDummyEligibilityResponse();
		// String nhisId = eligibilityResponse.getNhisId();
		String patientId = eligibilityResponse.getPatientId();
		String status = eligibilityResponse.getStatus();
		BigDecimal benefitBalance = eligibilityResponse.getEligibilityBalance().get(0).getBenefitBalance();
		String code = eligibilityResponse.getEligibilityBalance().get(0).getCode();
		String term = eligibilityResponse.getEligibilityBalance().get(0).getTerm();
		
	    model.addAttribute("patientId", patientId);
	    model.addAttribute("status", status);
	    model.addAttribute("benefitBalance", benefitBalance);
	    model.addAttribute("code", code);
	    model.addAttribute("term", term);



        model.addAttribute("insurance",insurance);
		model.addAttribute("nhisNumber", insurance.getNhisNumber());
		model.addAttribute("isMember", isMember);
		System.out.println(model.addAttribute("nhisNumber", nhisNumber));

	return "index";
	        }
	}
	
    
	
}
