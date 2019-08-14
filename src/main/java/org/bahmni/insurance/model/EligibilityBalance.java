package org.bahmni.insurance.model;

import java.math.BigDecimal;

public class EligibilityBalance {
	
	private BigDecimal benefitBalance;
	private String category ;
	private String term;
	
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
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	
	

}
