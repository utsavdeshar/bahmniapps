package org.bahmni.insurance.model;

import java.util.List;

import org.hl7.fhir.dstu3.model.Coding;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EligibilityParam {

	@SerializedName("chfID")
	@Expose
	private String chfID;
	
	@SerializedName("serviceCode")
	@Expose
	private String serviceCode;
	
	
	@SerializedName("itemCode")
	@Expose
	private List<EligibilityItemRequest> itemCode;

	public String getChfID() {
		return chfID;
	}

	public void setChfID(String chfID) {
		this.chfID = chfID;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public List<EligibilityItemRequest> getItemCode() {
		return itemCode;
	}

	public void setItemCode(List<EligibilityItemRequest> itemCode) {
		this.itemCode = itemCode;
	}
}