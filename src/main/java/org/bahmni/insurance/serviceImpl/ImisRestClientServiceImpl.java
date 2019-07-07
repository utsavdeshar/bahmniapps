package org.bahmni.insurance.serviceImpl;

import static org.apache.log4j.Logger.getLogger;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bahmni.insurance.AppProperties;
import org.bahmni.insurance.ImisConstants;
import org.bahmni.insurance.client.RequestWrapperConverter;
import org.bahmni.insurance.client.RestTemplateFactory;
import org.bahmni.insurance.model.ClaimLineItem;
import org.bahmni.insurance.model.ClaimResponseModel;
import org.bahmni.insurance.model.ClaimTrackingModel;
import org.bahmni.insurance.model.EligibilityBalance;
import org.bahmni.insurance.model.EligibilityResponseModel;
import org.bahmni.insurance.service.AInsuranceClientService;
import org.hl7.fhir.dstu3.model.Claim;
import org.hl7.fhir.dstu3.model.ClaimResponse;
import org.hl7.fhir.dstu3.model.ClaimResponse.AdjudicationComponent;
import org.hl7.fhir.dstu3.model.ClaimResponse.ItemComponent;
import org.hl7.fhir.dstu3.model.EligibilityRequest;
import org.hl7.fhir.dstu3.model.EligibilityResponse;
import org.hl7.fhir.dstu3.model.EligibilityResponse.InsuranceComponent;
import org.hl7.fhir.dstu3.model.Task;
import org.hl7.fhir.exceptions.FHIRException;
import org.openmrs.module.fhir.api.client.ClientHttpEntity;
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

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;

@Component
public class ImisRestClientServiceImpl extends AInsuranceClientService {
	private final RestTemplate restTemplate;
	private final IParser parsear = FhirContext.forDstu3().newJsonParser();
	private final org.apache.log4j.Logger logger = getLogger(ImisRestClientServiceImpl.class);

	private AppProperties properties;

	public ImisRestClientServiceImpl(AppProperties prop) {
		properties = prop;
		restTemplate = getRestClient();
	}

	public RestTemplate getRestClient() {
		RestTemplateFactory restFactory = new RestTemplateFactory(properties);
		return restFactory.getRestTemplate(100); // TODO: remove hardcoded
	}

	private ResponseEntity<String> sendPostRequest(Object object, String url)
			throws RestClientException, URISyntaxException {
		ClientHelper helper = getClientHelper(ImisConstants.REST_CLIENT);
		prepareRestTemplate(helper);
		ClientHttpEntity<?> request = helper.createRequest(properties.imisUrl + url, object);
		return exchange(helper, request, String.class);
	}

	private ResponseEntity<String> sendGetRequest(String url) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

	}

	private ResponseEntity<String> exchange(ClientHelper helper, ClientHttpEntity<?> request, Class<String> clazz) {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<Object>(request.getBody(), headers);
		return restTemplate.exchange(request.getUrl(), request.getMethod(), entity, clazz);
	}

	/*
	 * private HttpHeaders setRequestHeaders(ClientHelper clientHelper, HttpHeaders
	 * headers) { for (ClientHttpRequestInterceptor interceptor :
	 * clientHelper.getCustomInterceptors(properties.imisUser,
	 * properties.imisPassword)) { interceptor.addToHeaders(headers); } return
	 * headers; }
	 */

	private void prepareRestTemplate(ClientHelper clientHelper) {
		List<HttpMessageConverter<?>> converters = new ArrayList<>(clientHelper.getCustomMessageConverter());
		converters.add(new RequestWrapperConverter());
		restTemplate.setMessageConverters(converters);
	}

	@Override
	public ClaimResponse getClaimResponse(Claim claimRequest) throws RestClientException, URISyntaxException {
		ResponseEntity<String> responseObject = sendPostRequest(claimRequest, "/claimURL"); // TODO:
		ClaimResponse claimResponse = (ClaimResponse) parsear.parseResource(responseObject.getBody());
		return claimResponse;
	}

	@Override
	public EligibilityResponseModel getElibilityResponse(EligibilityRequest eligbilityRequest)
			throws RestClientException, URISyntaxException, FHIRException {
		ResponseEntity<String> responseObject = sendPostRequest(eligbilityRequest, "/eligURL");// TODO:
		EligibilityResponse eligibilityResponse = (EligibilityResponse) parsear.parseResource(responseObject.getBody());
		return populateEligibilityRespModel(eligibilityResponse);
	}

	@Override
	public ClaimResponse getClaimStatus(Task claimStatusRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClaimResponseModel getDummyClaimResponse(Claim claimRequest) {
		ResponseEntity<String> claimResponseSample = sendGetRequest(properties.dummyClaimResponseUrl);
		String claimResponseBody = claimResponseSample.getBody();
		ClaimResponse dummyClaimResponse = (ClaimResponse) parsear.parseResource(claimResponseBody);
		String jsonClaimRequest = parsear.encodeResourceToString(claimRequest);
		logger.debug("jsonClaimRequest ==> " + jsonClaimRequest);
		return populateClaimRespModel(dummyClaimResponse);

	}

	private ClaimResponseModel populateClaimRespModel(ClaimResponse claimResponse) {
		ClaimResponseModel clmRespModel = new ClaimResponseModel();
		clmRespModel.setClaimId(claimResponse.getId());
		clmRespModel.setApprovedTotal(claimResponse.getTotalBenefit().getValue());
		clmRespModel.setClaimedTotal(claimResponse.getTotalCost().getValue());
		clmRespModel.setClaimStatus(claimResponse.getStatus().toString());
		clmRespModel.setPaymentType(claimResponse.getPayment().getType().getCoding().get(0).getCode());
		clmRespModel.setOutCome(claimResponse.getOutcome().getCoding().get(0).getCode());
		clmRespModel.setDateProcessed(claimResponse.getPayment().getDate());

		List<ClaimLineItem> claimLineItems = new ArrayList<>();
		for (ItemComponent responseItem : claimResponse.getItem()) {
			ClaimLineItem claimItem = new ClaimLineItem();
			claimItem.setSequence(responseItem.getSequenceLinkIdElement().getValue());

			for (AdjudicationComponent adj : responseItem.getAdjudication()) {
				if (ImisConstants.ADJUDICATION_ELIGIBLE
						.equalsIgnoreCase(adj.getCategory().getCoding().get(0).getCode())) {
					claimItem.setTotalClaimed(adj.getAmount().getValue());
				}
				if (ImisConstants.ADJUDICATION_BENEFIT
						.equalsIgnoreCase(adj.getCategory().getCoding().get(0).getCode())) {
					claimItem.setTotalApproved(adj.getAmount().getValue());
				}
			}

			claimLineItems.add(claimItem);
		}
		clmRespModel.setClaimLineItems(claimLineItems);
		return clmRespModel;

	}

	@Override
	public EligibilityResponseModel getDummyEligibilityResponse() throws FHIRException {
		ResponseEntity<String> eligibiltyResponseSample = sendGetRequest(properties.dummyEligibiltyResponseUrl);
		String eligibilityResponseBody = eligibiltyResponseSample.getBody();
		EligibilityResponse dummyEligibiltyResponse = (EligibilityResponse) parsear
				.parseResource(eligibilityResponseBody);
		return populateEligibilityRespModel(dummyEligibiltyResponse);
	}

	private EligibilityResponseModel populateEligibilityRespModel(EligibilityResponse eligibilityResponse)
			throws FHIRException {
		EligibilityResponseModel eligRespModel = new EligibilityResponseModel();
		eligRespModel.setNhisId(eligibilityResponse.getId());
		eligRespModel.setPatientId(eligibilityResponse.getId());
		eligRespModel.setStatus(eligibilityResponse.getStatus().toString());

		List<EligibilityBalance> eligibilityBalance = new ArrayList<>();
		for (InsuranceComponent responseItem : eligibilityResponse.getInsurance()) {
			EligibilityBalance eligBalance = new EligibilityBalance();
			eligBalance.setCode(responseItem.getBenefitBalance().get(0).getTerm().getCoding().get(0).getCode());
			eligBalance.setTerm(responseItem.getBenefitBalance().get(0).getFinancial().get(0).getType().getCoding()
					.get(0).getCode());
			eligBalance.setBenefitBalance(
					responseItem.getBenefitBalance().get(0).getFinancial().get(0).getAllowedMoney().getValue());
			eligibilityBalance.add(eligBalance);
			// nhisId
			// patientId
			// status
			// Balance
		}

		eligRespModel.setEligibilityBalance(eligibilityBalance);

		return eligRespModel;
	}

	@Override
	public ClaimTrackingModel getDummyClaimTrack() {
		ResponseEntity<String> claimTrackingSample = sendGetRequest(properties.dummyClaimTrackUrl);
		String claimTrackingBody = claimTrackingSample.getBody();
		Task dummyClaimTrack = (Task) parsear.parseResource(claimTrackingBody);
		return populateClaimTrackModel(dummyClaimTrack);

	}

	private ClaimTrackingModel populateClaimTrackModel(Task task) {
		ClaimTrackingModel clmTrackModel = new ClaimTrackingModel();
		clmTrackModel.setClaimId(task.getId());
		clmTrackModel.setClaimOwner(task.getOwner().getDisplay());
		clmTrackModel.setClaimStatus(task.getStatus().toString());
		clmTrackModel.setClaimDesc(task.getDescription());
		clmTrackModel.setClaimSignature(task.getRelevantHistory().get(0).getDisplay());
		clmTrackModel.setDateProcessed(task.getExecutionPeriod().getStart());
		clmTrackModel.setDateAuthorized(task.getAuthoredOn());
		clmTrackModel.setDateLastModified(task.getLastModified());
		return clmTrackModel;
	}

	@Override
	public ResponseEntity<String> loginCheck() {
		return sendGetRequest(properties.imisUrl);
	}

}
