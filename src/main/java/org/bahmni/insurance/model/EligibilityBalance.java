package org.bahmni.insurance.model;

import java.math.BigDecimal;

public class EligibilityBalance {
	
	private BigDecimal benefitBalance;
	private String code ;
	private String term;
	
	public BigDecimal getBenefitBalance() {
		return benefitBalance;
	}
	public void setBenefitBalance(BigDecimal benefitBalance) {
		this.benefitBalance = benefitBalance;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	
	
	

}
