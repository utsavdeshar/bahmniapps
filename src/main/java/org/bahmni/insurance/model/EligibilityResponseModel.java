package org.bahmni.insurance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EligibilityResponseModel {

	private String nhisId;
	private String patientId;
	private Boolean policy;

	private List<EligibilityBalance> eligibilityBalance;

	
	

	public String getNhisId() {
		return nhisId;
	}

	public void setNhisId(String nhisId) {
		this.nhisId = nhisId;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	
	public Boolean getPolicy() {
		return policy;
	}
	public void setPolicy(Boolean policy) {
		this.policy = policy;
	}
	
	
	public List<EligibilityBalance> getEligibilityBalance() {
		return eligibilityBalance;
	}

	public void setEligibilityBalance(List<EligibilityBalance> eligibilityBalance) {
		this.eligibilityBalance = eligibilityBalance;
	}

	

}
