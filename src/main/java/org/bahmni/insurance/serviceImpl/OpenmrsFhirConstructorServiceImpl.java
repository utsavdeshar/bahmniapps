package org.bahmni.insurance.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.service.AOpernmrsFhirConstructorService;
import org.bahmni.insurance.validation.FhirInstanceValidator;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.Claim.ClaimStatus;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Task;
import org.hl7.fhir.dstu3.model.Task.TaskStatus;
import org.hl7.fhir.dstu3.model.EligibilityRequest.EligibilityRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

@Component
@Configurable
public class OpenmrsFhirConstructorServiceImpl extends AOpernmrsFhirConstructorService {
	
	@Autowired
	private AppProperties properties;

	@Override
	public String getFhirPatient(String patientId) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return this.getApiClient()
				.exchange(properties.openmrsFhirUrl + patientId, HttpMethod.GET, entity, String.class).getBody();
	}

	
	@Override
	public String constructFhirEligibilityRequest(String insuranceID) throws IOException {
		
		EligibilityRequest eligibilityRequest = new EligibilityRequest();
		
		List<Identifier> identifierList = new ArrayList<>();
		Identifier identifier = new Identifier();
		identifier.setSystem("SenderID");
		identifier.setValue(insuranceID);
		identifierList.add(identifier); 
		eligibilityRequest.setIdentifier(identifierList);
		
		eligibilityRequest.setStatus(EligibilityRequestStatus.ACTIVE);

		Reference patientReference = new Reference();
		patientReference.setReference("Patient/"+insuranceID);
		eligibilityRequest.setPatient(patientReference);
		
		Reference referenceOrg = new Reference();
		referenceOrg.setReference("Organization/1");
		eligibilityRequest.setOrganization(referenceOrg);
		
		
		Reference referenceInsurer = new Reference();
		referenceInsurer.setReference("Organization/2");
		eligibilityRequest.setInsurer(referenceInsurer);
		String eligibilityRequestValidation = FhirContext.forDstu3().newJsonParser().encodeResourceToString(eligibilityRequest);
		 
		 return validateRequest(eligibilityRequestValidation);

	}
	
	@Override
	public String constructFhirClaimRequest(String insuranceID) throws IOException {
		Claim claimRequest = new Claim();
		
		List<Identifier> identifierList = new ArrayList<>();
		Identifier identifier = new Identifier();
		identifier.setSystem("SenderID");
		identifier.setValue(insuranceID);
		identifierList.add(identifier); 
		claimRequest.setIdentifier(identifierList);
		
		claimRequest.setStatus(ClaimStatus.ACTIVE);

		Reference patientReference = new Reference();
		patientReference.setReference("Patient/"+insuranceID);
		claimRequest.setPatient(patientReference);
		
		Reference referenceOrg = new Reference();
		referenceOrg.setReference("Organization/1");
		claimRequest.setOrganization(referenceOrg);
		
		
		Reference referenceInsurer = new Reference();
		referenceInsurer.setReference("Organization/2");
		claimRequest.setInsurer(referenceInsurer);
		
		String claimRequestValidation = FhirContext.forDstu3().newJsonParser().encodeResourceToString(claimRequest);
		 
		 return validateRequest(claimRequestValidation);
	}
	
	@Override
	public String constructFhirClaimTrackRequest(String insuranceID) throws IOException {
		Task claimTracking = new Task();
		
		List<Identifier> identifierList = new ArrayList<>();
		Identifier identifier = new Identifier();
		identifier.setSystem("SenderID");
		identifier.setValue(insuranceID);
		identifierList.add(identifier); 
		claimTracking.setIdentifier(identifierList);
		
		claimTracking.setStatus(TaskStatus.READY);
		
		Reference referenceOrg = new Reference();
		referenceOrg.setReference("Organization/1");
		claimTracking.setOwner(referenceOrg);
		
		String trackingRequestValidation = FhirContext.forDstu3().newJsonParser().encodeResourceToString(claimTracking);
		 
		 return validateRequest(trackingRequestValidation);
		
	}
	
	private String validateRequest(String eligibilityRequestValidation) throws IOException {
		FhirContext ctx = FhirContext.forDstu3();
		 
		// Create a FhirInstanceValidator and register it to a validator
		FhirValidator validator = ctx.newValidator();
		FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
		validator.registerValidatorModule(instanceValidator);
		instanceValidator.setAnyExtensionsAllowed(true);
		
       //validate
		ValidationResult result = validator.validateWithResult(eligibilityRequestValidation);
    
 
		//error checking
		 if (result.isSuccessful() == false) {
			 for (SingleValidationMessage next : result.getMessages()) {
					System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - " + next.getMessage());
				}
		   }else {
			   System.out.println("validation sucessful");
		   }		 
		return(eligibilityRequestValidation);
	}


}
