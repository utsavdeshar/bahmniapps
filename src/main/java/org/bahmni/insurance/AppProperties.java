package org.bahmni.insurance;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppProperties {

	@Value("${openmrs.db.url}")
	public String openmrsDBUrl;

	@Value("${openelis.db.url}")
	public String openelisDBUrl;

	@Value("${imisconnect.user}")
	public String imisConnectUser;

	@Value("${imisconnect.password}")
	public String imisConnectPassword;

	@Value("${openimis.api.url}")
	public String imisUrl;

	@Value("${openimis.api.user}")
	public String imisUser;

	@Value("${openimis.api.password}")
	public String imisPassword;

	@Value("${openmrs.fhir.url}")
	public String openmrsFhirUrl;

	@Value("${openmrs.password}")
	public String openmrsPassword;

	@Value("${openmrs.user}")
	public String openmrsUser;

	@Value("${log4j.config.file}")
	public String log4jConfigFile;

	@Value("${openmrs.odoo.api}")
	public String openmrsOdooApi;

	
	@Value("${dummy.claimResponse.url}")
	public String dummyClaimResponseUrl;
	

	@Value("${dummy.eligibilityResponse.url}")
	public String dummyEligibiltyResponseUrl;
	
	@Value("${dummy.claimTrack.url}")
	public String dummyClaimTrackUrl;
	
	
	
	

	
}
