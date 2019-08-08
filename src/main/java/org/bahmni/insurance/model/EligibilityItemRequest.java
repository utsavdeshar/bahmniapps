package org.bahmni.insurance.model;

import java.math.BigDecimal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EligibilityItemRequest {

	@SerializedName("category")
	@Expose
	private String category;

	
	@SerializedName("sequence")
	@Expose
	private Integer sequence;

	@SerializedName("allowedMoney")
	@Expose
	private BigDecimal allowedMoney;
	
	
	
	@SerializedName("code")
	@Expose
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public BigDecimal getAllowedMoney() {
		return allowedMoney;
	}

	public void setAllowedMoney(BigDecimal allowedMoney) {
		this.allowedMoney = allowedMoney;
	}

	

	

}
