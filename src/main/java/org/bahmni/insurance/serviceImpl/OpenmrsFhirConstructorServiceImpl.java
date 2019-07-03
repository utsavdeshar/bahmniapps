package org.bahmni.insurance.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bahmni.insurance.AppProperties;
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
	public Claim constructFhirClaimRequest(String claimId) {
		
		Claim claimReq = new Claim();
		
		//claim  number
		List<Identifier> identifierList = new ArrayList<>();
		
		Identifier identifier1 = new Identifier();
		CodeableConcept codeableConcept1 = new CodeableConcept();
		Coding code1 = new Coding();
		code1.setSystem("https://hl7.org/fhir/valueset-identifier-type.html"); //TODO:
		code1.setCode("ACSN"); //TODO:
		codeableConcept1.addCoding(code1);
		identifier1.setType(codeableConcept1);
		identifier1.setUse(IdentifierUse.USUAL);
		identifier1.setValue("8");
		identifierList.add(identifier1); 
		
		Identifier identifier2 = new Identifier();
		CodeableConcept codeableConcept2 = new CodeableConcept();
		Coding code2 = new Coding();
		code2.setSystem("https://hl7.org/fhir/valueset-identifier-type.html"); //TODO:
		code2.setCode("MR"); //TODO:
		codeableConcept2.addCoding(code2);
		identifier2.setType(codeableConcept2);
		identifier2.setUse(IdentifierUse.USUAL);
		identifier2.setValue("clCode");
		identifierList.add(identifier2); 
		
		
		/*identifier.setType(value)
		identifier.setSystem("ClaimID");
		identifier.setValue(claimId);*/
		
		claimReq.setIdentifier(identifierList);
		
		//Insuree/patient
		Reference patientReference = new Reference();
		patientReference.setReference("Patient/"+insuranceID);
		claimReq.setPatient(patientReference);
		
		//BillablePeriod 
		Period period = new Period();
		period.setStart(startDate);
		period.setEnd(endDate);
		claimReq.setBillablePeriod(period);
		
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
		
		//"enterer"
		Reference entererReference = new Reference();
		entererReference.setReference("Practitioner/"+practionerId);
		claimReq.setEnterer(entererReference);
		
		//"Facility"
		Reference facilityReference = new Reference();
		facilityReference.setReference("Location/"+hfCode); //TODO: healthFacility code required. may be from properties
		claimReq.setEnterer(entererReference);
		
		return claimReq;
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
