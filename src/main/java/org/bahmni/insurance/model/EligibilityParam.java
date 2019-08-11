package org.bahmni.insurance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EligibilityParam {

	@SerializedName("chfID")
	@Expose
	private String chfID;

	public String getChfID() {
		return chfID;
	}

	public void setChfID(String chfID) {
		this.chfID = chfID;
	}

	
}