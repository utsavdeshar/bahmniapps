package org.bahmni.insurance.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListClaimItem {

	@SerializedName("item")
	@Expose
	private List<ClaimLineItem> item ;

	public List<ClaimLineItem> getItem() {
		return item;
	}

	public void setItem(List<ClaimLineItem> item) {
		this.item = item;
	}

}