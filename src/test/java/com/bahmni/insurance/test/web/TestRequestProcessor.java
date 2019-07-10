package com.bahmni.insurance.test.web;

import static org.junit.Assert.assertEquals;

import javax.sql.DataSource;

import org.bahmni.insurance.SpringBootConsoleApplication;
import org.bahmni.insurance.dao.FhirResourceDaoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(SpringBootConsoleApplication.class)
@WebAppConfiguration
@ContextConfiguration(classes = SpringBootConsoleApplication.class)
public class TestRequestProcessor {

	@Autowired
	protected MockMvc mvc;

	@MockBean
	private DataSource datasource;

	@MockBean
	private FhirResourceDaoServiceImpl fhirResourceDaoServiceImpl;

	@Test
	@WithMockUser
	public void testRetrievePatient() throws Exception {
		MvcResult mvcresult = mvc.perform(MockMvcRequestBuilders.get("/patient/f3013eb6-4650-4025-96c8-9a8238a9e59f")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
		int status = mvcresult.getResponse().getStatus();
		assertEquals(200, status);
	}
	
	@Test
	@WithMockUser
	public void testCreatePatient() throws Exception {
		String inputJson = "{\r\n" + 
				"  \"resourceType\": \"Person\",\r\n" + 
				"  \"id\": \"dda12af7-1691-11df-97a5-7038c432aabf\",\r\n" + 
				"  \"name\": [\r\n" + 
				"    {\r\n" + 
				"      \"use\": \"official\",\r\n" + 
				"      \"family\": [\r\n" + 
				"        \"Chalmers\"\r\n" + 
				"      ],\r\n" + 
				"      \"given\": [\r\n" + 
				"        \"Peter\",\r\n" + 
				"        \"James\"\r\n" + 
				"      ]\r\n" + 
				"    },\r\n" + 
				"    {\r\n" + 
				"      \"use\": \"usual\",\r\n" + 
				"      \"given\": [\r\n" + 
				"        \"Jim\"\r\n" + 
				"      ]\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"gender\": \"male\",\r\n" + 
				"  \"birthDate\": \"1974-12-25\",\r\n" + 
				"  \"address\": [\r\n" + 
				"    {\r\n" + 
				"      \"use\": \"home\",\r\n" + 
				"      \"line\": [\r\n" + 
				"        \"534 Erewhon St\"\r\n" + 
				"      ],\r\n" + 
				"      \"city\": \"PleasantVille\",\r\n" + 
				"      \"state\": \"Vic\",\r\n" + 
				"      \"postalCode\": \"3999\"\r\n" + 
				"    }\r\n" + 
				"  ],\r\n" + 
				"  \"active\": true\r\n" + 
				"}";
		
		 MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/patient")
			      .contentType("application/fhir+json;q=1.0, application/json+fhir;q=0.9").content(inputJson)).andReturn();
			   
		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
	}


}
