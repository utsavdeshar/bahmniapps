package org.bahmni.insurance.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ClaimResponseModel {

	private String claimId;
	private String nhisId;
	private String patientId;
	private String policyStatus;
	private BigDecimal claimedTotal;
	private BigDecimal approvedTotal;
	private Date dateCreated;
	private Date dateProcessed;
	private String rejectionReason;
	private String outCome;
	private String claimStatus;
	private String paymentType;
	private List<ClaimLineItem> claimLineItems;

/*	public enum ClaimStatus {
		REJECTED, ENTERED, CHECKED, PROCESSED, VALUATED
	};
	
	public enum PaymentType {
		COMPLETE, PARTIAL
	};*/

	
	public String getClaimId() {
		return claimId;
	}

	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}

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

	public String getPolicyStatus() {
		return policyStatus;
	}

	public void setPolicyStatus(String policyStatus) {
		this.policyStatus = policyStatus;
	}

	public BigDecimal getClaimedTotal() {
		return claimedTotal;
	}

	public void setClaimedTotal(BigDecimal claimedTotal) {
		this.claimedTotal = claimedTotal;
	}

	public BigDecimal getApprovedTotal() {
		return approvedTotal;
	}

	public void setApprovedTotal(BigDecimal approvedTotal) {
		this.approvedTotal = approvedTotal;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateProcessed() {
		return dateProcessed;
	}

	public void setDateProcessed(Date dateProcessed) {
		this.dateProcessed = dateProcessed;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public List<ClaimLineItem> getClaimLineItems() {
		return claimLineItems;
	}

	public void setClaimLineItems(List<ClaimLineItem> claimLineItems) {
		this.claimLineItems = claimLineItems;
	}

	public String getClaimStatus() {
		return claimStatus;
	}

	public void setClaimStatus(String claimStatus) {
		this.claimStatus = claimStatus;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getOutCome() {
		return outCome;
	}

	public void setOutCome(String outCome) {
		this.outCome = outCome;
	}

	

}
