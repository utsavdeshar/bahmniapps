package org.bahmni.insurance.model;

import java.math.BigDecimal;

import org.hl7.fhir.dstu3.model.PositiveIntType;

public class ClaimLineItem {

	private int sequenceLinkId;
	private int quantityProvided;
	private int quantityApproved;
	private BigDecimal totalBenefit;
	private BigDecimal totalCost;
	private String explanation;
	private String rejectionReason;

	public int getSequenceLinkId() {
		return sequenceLinkId;
	}

	public void setSequenceLinkId(int sequenceLinkId) {
		this.sequenceLinkId = sequenceLinkId;
	}

	public int getQuantityProvided() {
		return quantityProvided;
	}

	public void setQuantityProvided(int quantityProvided) {
		this.quantityProvided = quantityProvided;
	}

	public int getQuantityApproved() {
		return quantityApproved;
	}

	public void setQuantityApproved(int quantityApproved) {
		this.quantityApproved = quantityApproved;
	}

	public BigDecimal getTotalBenefit() {
		return totalBenefit;
	}

	public void setTotalBenefit(BigDecimal totalBenefit) {
		this.totalBenefit = totalBenefit;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
