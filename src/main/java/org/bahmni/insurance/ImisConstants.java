package org.bahmni.insurance;

public class ImisConstants {

	public final static int OPENIMIS_FHIR = 0;
	public final static int OPENMRS_FHIR = 1;
	public final static int OPENMRS_ODOO = 2;

	public static enum FHIR_RESOURCE_TYPE {
		CLAIM("CLAIM"),
		CLAIMRESPONSE("CLAIMRESPONSE"), 
		ELIGIBILITYREQUEST("ELIGIBILITYREQUEST"), 
		ELIGIBILITYRESPONSE("ELIGIBILITYRESPONSE"),
		CLAIMTRACK("CLAIMTRACK");
		
		private String value;
		 
		FHIR_RESOURCE_TYPE(String value) {
	        this.value = value;
	    }
	 
	    public String getValue() {
	        return value;
	    }
	};
	
	public final static String FHIR_VERSION = "STU3";


	public final static String FHIR_CLIENT = "fhir";
	public final static String REST_CLIENT = "rest";
	public final static String ADJUDICATION_ELIGIBLE = "eligible";
	public final static String ADJUDICATION_BENEFIT = "benefit";
	public final static String CLAIM_ID = "claimId";
	public final static String CLAIM_ITEMS = "item";

	public final static String INSUREE_ID = "insureeId";
	public final static String PATIENT_UUID = "patientUUID";
	public final static String VISIT_UUID = "visitUUID";
	public final static String FHIR_CODE_FOR_IMIS_CLAIM_CODE_TYPE = "MR";
	public final static String FHIR_VALUESET_SYSTEM = "https://hl7.org/fhir/valueset-identifier-type.html";

}
