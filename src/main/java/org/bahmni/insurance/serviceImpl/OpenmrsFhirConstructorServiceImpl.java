package org.bahmni.insurance.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.service.AOpernmrsFhirConstructorService;
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
	public EligibilityRequest constructFhirEligibilityRequest(String insuranceID) {
		
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
		
		return eligibilityRequest;
	}
	
	@Override
	public Claim constructFhirClaimRequest(String insuranceID) {
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
		
		return claimRequest;
	}
	
	@Override
	public Task constructFhirClaimTrackRequest(String insuranceID) {
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
	
		
		return claimTracking;
	}


}
