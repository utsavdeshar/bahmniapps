package org.bahmni.insurance.service;

import java.io.IOException;

import org.bahmni.insurance.model.MedicalData;
import org.springframework.stereotype.Component;

@Component
public abstract class MedicalDataService {	



	public abstract MedicalData constructMedicalDataRequest(String patienUUID, String vistUUID)throws IOException;






}

