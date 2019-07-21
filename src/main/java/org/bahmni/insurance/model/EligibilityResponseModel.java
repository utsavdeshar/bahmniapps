package org.bahmni.insurance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EligibilityResponseModel {

	private String nhisId;
	private String patientId;
	private String status;
	private Date cardIssued;
	private Date validityFrom;
	private Date validityTo;
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
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCardIssued() {
		return cardIssued;
	}

	public void setCardIssued(Date cardIssued) {
		this.cardIssued = cardIssued;
	}

	public Date getValidityFrom() {
		return validityFrom;
	}

	public void setValidityFrom(Date validityFrom) {
		this.validityFrom = validityFrom;
	}

	public Date getValidityTo() {
		return validityTo;
	}

	public void setValidityTo(Date validityTo) {
		this.validityTo = validityTo;
	}

	public List<EligibilityBalance> getEligibilityBalance() {
		return eligibilityBalance;
	}

	public void setEligibilityBalance(List<EligibilityBalance> eligibilityBalance) {
		this.eligibilityBalance = eligibilityBalance;
	}

	

}
