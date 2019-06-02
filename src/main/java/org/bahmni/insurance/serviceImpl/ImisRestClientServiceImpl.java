package org.bahmni.insurance.serviceImpl;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RequestWrapperConverter;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.bahmni.insurance.service.AInsuranceClientService;
import org.bahmni.insurance.utils.InsuranceUtils;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityResponse;
import org.hl7.fhir.dstu3.model.Task;
import org.openmrs.module.fhir.api.client.ClientHttpEntity;
import org.openmrs.module.fhir.api.client.ClientHttpRequestInterceptor;
import org.openmrs.module.fhir.api.helper.ClientHelper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Component
public class ImisRestClientServiceImpl extends AInsuranceClientService {
	private RestTemplate restTemplate;
	private final Gson defaultJsonParser = InsuranceUtils.createDefaultGson();

	
	private AppProperties properties;
	
	public ImisRestClientServiceImpl(AppProperties prop) {
		properties = prop;
		restTemplate = getRestClient();
	}

	public RestTemplate getRestClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory(properties);
		return restFactory.getRestTemplate(ImisConstants.OPENIMIS_FHIR);
	}

	private ResponseEntity<String> sendPostRequest(Object object) throws RestClientException, URISyntaxException {
		ClientHelper helper = getClientHelper(ImisConstants.REST_CLIENT);
		prepareRestTemplate(helper);
		ClientHttpEntity<?> request = helper.createRequest(properties.imisUrl, object);
		return exchange(helper, request, String.class);
	}

	private ResponseEntity<String> sendGetRequest() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(properties.imisUrl, HttpMethod.GET, entity, String.class);

	}

	private ResponseEntity<String> exchange(ClientHelper helper, ClientHttpEntity<?> request, Class<String> clazz) {
		/*
		 * HttpHeaders headers = new HttpHeaders();
		 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON)); HttpEntity
		 * <String> entity = new HttpEntity<String>(headers);
		 */

		HttpHeaders headers = new HttpHeaders();
		// setRequestHeaders(helper, headers);
		HttpEntity<?> entity = new HttpEntity<Object>(request.getBody(), headers);
		return restTemplate.exchange(request.getUrl(), request.getMethod(), entity, clazz);
	}

	private HttpHeaders setRequestHeaders(ClientHelper clientHelper, HttpHeaders headers) {
		for (ClientHttpRequestInterceptor interceptor : clientHelper
				.getCustomInterceptors(properties.imisUser, properties.imisPassword)) {
			interceptor.addToHeaders(headers);
		}
		return headers;
	}

	private void prepareRestTemplate(ClientHelper clientHelper) {
		List<HttpMessageConverter<?>> converters = new ArrayList<>(clientHelper.getCustomMessageConverter());
		converters.add(new RequestWrapperConverter());
		restTemplate.setMessageConverters(converters);
	}

	@Override
	public ClaimResponse getClaimResponse(Claim claimRequest) throws RestClientException, URISyntaxException {
		ResponseEntity<String> responseObject = sendPostRequest(claimRequest);
		return defaultJsonParser.fromJson(responseObject.toString(), ClaimResponse.class);
	}

	@Override
	public EligibilityResponse getElibilityResponse(EligibilityRequest eligbilityRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClaimResponse getClaimStatus(Task claimStatusRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<String> loginCheck() {
		return sendGetRequest();
	}

}
