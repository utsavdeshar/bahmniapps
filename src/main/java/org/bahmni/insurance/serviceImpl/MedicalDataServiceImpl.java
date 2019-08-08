package org.bahmni.insurance.serviceImpl;

import java.io.IOException;
import java.net.HttpCookie;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.bahmni.insurance.model.MedicalData;
import org.bahmni.insurance.service.MedicalDataService;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

@Component
public class MedicalDataServiceImpl extends MedicalDataService {
	private final RestTemplate restTemplate;
	private final IParser parsear = FhirContext.forDstu3().newJsonParser();

	private AppProperties properties;

	public MedicalDataServiceImpl(AppProperties prop) {
		properties = prop;
		restTemplate = getRestClient();

	}

	public RestTemplate getRestClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory(properties);
		return restFactory.getRestTemplate(ImisConstants.OPENIMIS_FHIR);
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

	private String getCSRFToken() {

		HttpHeaders headers = createHeaders(properties.imisUser, properties.imisPassword);
		// headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		HttpHeaders responseHeaders = restTemplate.exchange(properties.imisUrl, HttpMethod.GET, entity, String.class)
				.getHeaders();
		// return responseHeaders.get("Set-Cookie").get(0);

		List<HttpCookie> cookies = HttpCookie.parse(responseHeaders.get("Set-Cookie").get(0));
		String csrfToken = null;
		for (HttpCookie c : cookies) {
			if ("csrftoken".equals(c.getName())) {
				csrfToken = c.getValue();
				break;
			}
		}
		return csrfToken;

	}

	private ResponseEntity<String> sendPostRequest(String requestJson, String url) {

		HttpHeaders headers = createHeaders(properties.imisUser, properties.imisPassword);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("Content-Type", "application/json");
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

	}

	private String sendGetRequest(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

	}
	
	@Override
	public MedicalData constructMedicalDataRequest(String patienUUID, String vistUUID) throws IOException {
		String diagnosisUrl = properties.openmrsRootUrl+"/bahmnicore/diagnosis/search?patientUuid="+patienUUID +"&visitUuid="+vistUUID;
		String medicalDataBody = sendGetRequest(diagnosisUrl);
		MedicalData MedicalData = (MedicalData) parsear.parseResource(medicalDataBody);
		return populateMedicalDataModel(MedicalData);
	}
	
	

	private MedicalData populateMedicalDataModel(MedicalData medicalData) {
		MedicalData medicalDataModel = new MedicalData();
		medicalDataModel.setUuid(medicalData.getUuid());
		medicalDataModel.setName(medicalData.getName());
		medicalDataModel.setDatatype(medicalData.getDatatype());
		medicalDataModel.setSet(medicalData.isSet());
		medicalDataModel.setConceptClass(medicalData.getConceptClass());
		medicalDataModel.setDescription(medicalData.getDescription());
		medicalDataModel.setDateCreated(medicalData.getDateCreated());
		medicalDataModel.setDateChanged(medicalData.getDateChanged());

	
		return medicalDataModel;

	}



	

	

}
