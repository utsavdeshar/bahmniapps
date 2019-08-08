package org.bahmni.insurance.model;

import java.sql.Date;

public class MedicalData {
	private String uuid;
	private String name;
	private String datatype;
	private boolean isSet;
	private String conceptClass;
	private String description;
	private Date dateCreated;
	private Date dateChanged;
	
	public MedicalData() {}
	
	public MedicalData(String uuid, String name, String datatype, boolean isSet, String conceptClass, 
			String description, Date dateCreated, Date dateChanged) {
	this.uuid=uuid;
	this.name=name;
	this.datatype=datatype;
	this.isSet=isSet;
	this.conceptClass=conceptClass;
	this.description=description;
	this.dateCreated=dateCreated;
	this.dateChanged=dateChanged;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public boolean isSet() {
		return isSet;
	}
	public void setSet(boolean isSet) {
		this.isSet = isSet;
	}
	public String getConceptClass() {
		return conceptClass;
	}
	public void setConceptClass(String conceptClass) {
		this.conceptClass = conceptClass;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateChanged() {
		return dateChanged;
	}
	public void setDateChanged(Date dateChanged) {
		this.dateChanged = dateChanged;
	}
	

}
