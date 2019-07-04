package org.bahmni.insurance.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.service.AOpernmrsFhirConstructorService;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Identifier.IdentifierUse;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.Claim.DiagnosisComponent;
import org.hl7.fhir.dstu3.model.Claim.ItemComponent;
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
public class FhirConstructorServiceImpl extends AOpernmrsFhirConstructorService {
	
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
	public Claim constructFhirClaimRequest(Map<String, Object> claimParams) {
		
		Claim claimReq = new Claim();
		
		//claim  number
		List<Identifier> identifierList = new ArrayList<>();
		Identifier identifier2 = new Identifier();
		CodeableConcept codeableConcept2 = new CodeableConcept();
		Coding code2 = new Coding();
		code2.setSystem(ImisConstants.FHIR_VALUESET_SYSTEM);
		code2.setCode(ImisConstants.FHIR_CODE_FOR_IMIS_CLAIM_CODE_TYPE);
		codeableConcept2.addCoding(code2);
		identifier2.setType(codeableConcept2);
		identifier2.setUse(IdentifierUse.USUAL);
		identifier2.setValue((String) claimParams.get(ImisConstants.CLAIM_ID)); 
		identifierList.add(identifier2); 
		claimReq.setIdentifier(identifierList);
		
		//Insuree patient
		Reference patientReference = new Reference();
		patientReference.setReference("Patient/"+(String) claimParams.get(ImisConstants.INSUREE_ID));
		claimReq.setPatient(patientReference);
		
		//BillablePeriod 
		Period period = new Period();
		period.setStart(new Date());
		period.setEnd(new Date());
		claimReq.setBillablePeriod(period);
		claimReq.setCreated(new Date());
		
		//Diagnosis : //TODO: retrieve diagnosis from openmrs 
		List<DiagnosisComponent> listDiagnosis = new ArrayList<>();
		DiagnosisComponent diagnosis = new DiagnosisComponent();
		diagnosis.setSequence(1);
		CodeableConcept codeableConcept = new CodeableConcept();
		Coding code = new Coding();
		code.setSystem("https://icd.who.int/browse10/2010/en"); //TODO:
		code.setCode("ICD10-code"); //TODO:
		code.setDisplay("Diagnosis Name");//TODO:
		codeableConcept.addCoding(code);
		diagnosis.addType(codeableConcept);
		listDiagnosis.add(diagnosis);
		claimReq.setDiagnosis(listDiagnosis);
		
		//Items/services for claims
		
		List<ItemComponent> listItem = (List<ItemComponent>) claimParams.get(ImisConstants.CLAIM_ITEMS);
		//System.out.println("listItem : "+listItem);
		//System.out.println("listItem : "+listItem.get(0).getCategory().getText());

		
			
		
		claimReq.setItem(listItem);
		
		
		
		//"enterer"
		Reference entererReference = new Reference();
		entererReference.setReference("Practitioner/"+properties.openImisEntererId);
		claimReq.setEnterer(entererReference);
		
		//"Facility"
		Reference facilityReference = new Reference();
		facilityReference.setReference("Location/"+properties.openImisHFCode); 
		claimReq.setEnterer(facilityReference);
		
		return claimReq;
	}
	
	private void populateClaimableItems() {
		
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

}
