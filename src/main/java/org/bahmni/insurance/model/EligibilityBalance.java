package org.bahmni.insurance.model;

import java.math.BigDecimal;

public class EligibilityBalance {
	
	private BigDecimal benefitBalance;
	private String category ;
	private String validDate;
	
	public BigDecimal getBenefitBalance() {
		return benefitBalance;
	}
	public void setBenefitBalance(BigDecimal benefitBalance) {
		this.benefitBalance = benefitBalance;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	
	
}
