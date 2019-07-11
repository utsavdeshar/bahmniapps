package org.bahmni.insurance.serviceImpl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.model.ClaimLineItem;
import org.bahmni.insurance.model.ListClaimItem;
import org.bahmni.insurance.service.AOpernmrsFhirConstructorService;
import org.bahmni.insurance.validation.FhirInstanceValidator;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.Claim.DiagnosisComponent;
import org.hl7.fhir.dstu3.model.Claim.ItemComponent;
import org.hl7.fhir.dstu3.model.CodeableConcept;
import org.hl7.fhir.dstu3.model.Coding;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityRequest.EligibilityRequestStatus;
import org.hl7.fhir.dstu3.model.Identifier;
import org.hl7.fhir.dstu3.model.Identifier.IdentifierUse;
import org.hl7.fhir.dstu3.model.Money;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.Reference;
import org.hl7.fhir.dstu3.model.SimpleQuantity;
import org.hl7.fhir.dstu3.model.Task;
import org.hl7.fhir.dstu3.model.Task.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.validation.FhirValidator;
import ca.uhn.fhir.validation.SingleValidationMessage;
import ca.uhn.fhir.validation.ValidationResult;

@Component
public class FhirConstructorServiceImpl extends AOpernmrsFhirConstructorService {

	@Autowired
	private AppProperties properties;

	@Override
	public String getFhirPatient(String name) {
		HttpHeaders headers = createHeaders(properties.openmrsUser, properties.openmrsPassword);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return this.getApiClient().exchange(properties.openmrsFhirUrl+"?name="+name, HttpMethod.GET, entity,
				String.class).getBody();
	}
	
	
	@Override
	public ResponseEntity<String> createFhirPatient(String patientJson) {
		HttpHeaders headers = createHeaders(properties.openmrsUser, properties.openmrsPassword);
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    headers.add("Content-Type", "application/fhir+json;q=1.0, application/json+fhir;q=0.9");
		HttpEntity<String> entity = new HttpEntity<String>(patientJson, headers);
		return this.getApiClient().exchange(properties.openmrsFhirUrl+"/", HttpMethod.POST, entity,
				String.class);
	}
	
	

	private HttpHeaders createHeaders(String username, String password) {
		return new HttpHeaders() {
			private static final long serialVersionUID = 1L;
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@Override
	public Claim constructFhirClaimRequest(Map<String, Object> claimParams) throws IOException {

		Claim claimReq = new Claim();

		// claim number
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

		// Insuree patient
		Reference patientReference = new Reference();
		patientReference.setReference("Patient/" + (String) claimParams.get(ImisConstants.INSUREE_ID));
		claimReq.setPatient(patientReference);

		// BillablePeriod
		Period period = new Period();
		period.setStart(new Date());
		period.setEnd(new Date());
		claimReq.setBillablePeriod(period);
		claimReq.setCreated(new Date());

		// Diagnosis : //TODO: retrieve diagnosis from openmrs
		List<DiagnosisComponent> listDiagnosis = new ArrayList<>();
		DiagnosisComponent diagnosis = new DiagnosisComponent();
		diagnosis.setSequence(1);
		CodeableConcept codeableConcept = new CodeableConcept();
		Coding code = new Coding();
		code.setSystem("https://icd.who.int/browse10/2010/en"); // TODO:
		code.setCode("ICD10-code"); // TODO:
		code.setDisplay("Diagnosis Name");// TODO:
		codeableConcept.addCoding(code);
		diagnosis.addType(codeableConcept);
		listDiagnosis.add(diagnosis);
		claimReq.setDiagnosis(listDiagnosis);

		// Items/services for claims

		List<ItemComponent> listItemComponent = populateClaimableItems(claimParams.get(ImisConstants.CLAIM_ITEMS));
		claimReq.setItem(listItemComponent);

		// "enterer"
		Reference entererReference = new Reference();
		entererReference.setReference("Practitioner/" + properties.openImisEntererId);
		claimReq.setEnterer(entererReference);

		// "Facility"
		Reference facilityReference = new Reference();
		facilityReference.setReference("Location/" + properties.openImisHFCode);
		claimReq.setEnterer(facilityReference);

		String claimRequestValidation = FhirContext.forDstu3().newJsonParser().encodeResourceToString(claimReq);

		boolean isValid = validateRequest(claimRequestValidation);

		return claimReq;
	}

	private List<ItemComponent> populateClaimableItems(Object claimsParamItem) {
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		String jsonStrItems = "{ \"item\": " + gson.toJson(claimsParamItem) + " } ";
		ListClaimItem listItem = gson.fromJson(jsonStrItems, ListClaimItem.class);
		List<ItemComponent> listItemComponent = new ArrayList<>();
		for (ClaimLineItem claimItem : listItem.getItem()) {
			ItemComponent itemComponent = new ItemComponent();
			itemComponent.setSequence(claimItem.getSequence());

			CodeableConcept codeConceptCategory = new CodeableConcept();
			codeConceptCategory.setText(claimItem.getCategory());
			itemComponent.setCategory(codeConceptCategory);

			SimpleQuantity simpleQuantity = new SimpleQuantity();
			simpleQuantity.setValue(claimItem.getQuantity());
			itemComponent.setQuantity(simpleQuantity);

			CodeableConcept codeConceptService = new CodeableConcept();
			codeConceptService.setText(claimItem.getService());
			itemComponent.setService(codeConceptService);

			Money value = new Money();
			value.setValue(claimItem.getUnitPrice());
			itemComponent.setUnitPrice(value);
			listItemComponent.add(itemComponent);
		}
		return listItemComponent;
	}

	@Override
	public EligibilityRequest constructFhirEligibilityRequest(String insuranceID) throws IOException {

		EligibilityRequest eligibilityRequest = new EligibilityRequest();

		List<Identifier> identifierList = new ArrayList<>();
		Identifier identifier = new Identifier();
		identifier.setSystem("SenderID");
		identifier.setValue(insuranceID);
		identifierList.add(identifier);
		eligibilityRequest.setIdentifier(identifierList);

		eligibilityRequest.setStatus(EligibilityRequestStatus.ACTIVE);

		Reference patientReference = new Reference();
		patientReference.setReference("Patient/" + insuranceID);
		eligibilityRequest.setPatient(patientReference);

		Reference referenceOrg = new Reference();
		referenceOrg.setReference("Organization/1");
		eligibilityRequest.setOrganization(referenceOrg);

		Reference referenceInsurer = new Reference();
		referenceInsurer.setReference("Organization/2");
		eligibilityRequest.setInsurer(referenceInsurer);

		String eligibilityRequestValidation = FhirContext.forDstu3().newJsonParser()
				.encodeResourceToString(eligibilityRequest);

		boolean isValid = validateRequest(eligibilityRequestValidation);

		return eligibilityRequest;
	}

	@Override
	public Task constructFhirClaimTrackRequest(String insuranceID) throws IOException {
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

		boolean isValid = validateRequest(trackingRequestValidation);

		return claimTracking;
	}

	private boolean validateRequest(String eligibilityRequestValidation) throws IOException {
		FhirContext ctx = FhirContext.forDstu3();

		// Create a FhirInstanceValidator and register it to a validator
		FhirValidator validator = ctx.newValidator();
		FhirInstanceValidator instanceValidator = new FhirInstanceValidator();
		validator.registerValidatorModule(instanceValidator);
		instanceValidator.setAnyExtensionsAllowed(true);

		// validate
		ValidationResult result = validator.validateWithResult(eligibilityRequestValidation);

		// error checking
		if (result.isSuccessful() == false) {
			for (SingleValidationMessage next : result.getMessages()) {
				System.out.println(" Next issue " + next.getSeverity() + " - " + next.getLocationString() + " - "
						+ next.getMessage());
			}
		} else {
			System.out.println("validation sucessful");
		}
		return result.isSuccessful();
	}

}
