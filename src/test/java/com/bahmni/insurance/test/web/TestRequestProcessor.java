package com.bahmni.insurance.test.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.sql.DataSource;

import org.bahmni.insurance.SpringBootConsoleApplication;
import org.bahmni.insurance.dao.FhirResourceDaoServiceImpl;
import org.hl7.fhir.dstu3.model.Bundle;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRequestProcessor extends AbstractWebTest {

	@Autowired
	protected MockMvc mvc;

	@MockBean
	private DataSource datasource;

	@MockBean
	private FhirResourceDaoServiceImpl fhirResourceDaoServiceImpl;

	@Test
	@WithMockUser
	public void createPatientTest() throws Exception {
		String personJson = "{\r\n" + "  \"resourceType\": \"Person\",\r\n"
				+ "  \"id\": \"dda12af7-1691-11df-97a5-7038c432aabf\",\r\n" + "  \"name\": [\r\n" + "    {\r\n"
				+ "      \"use\": \"official\",\r\n" + "      \"family\": [\r\n" + "        \"Chalmers\"\r\n"
				+ "      ],\r\n" + "      \"given\": [\r\n" + "        \"Dummy\",\r\n" + "        \"Test\"\r\n"
				+ "      ]\r\n" + "    },\r\n" + "    {\r\n" + "      \"use\": \"usual\",\r\n"
				+ "      \"given\": [\r\n" + "        \"Jim\"\r\n" + "      ]\r\n" + "    }\r\n" + "  ],\r\n"
				+ "  \"gender\": \"male\",\r\n" + "  \"birthDate\": \"1974-12-25\",\r\n" + "  \"address\": [\r\n"
				+ "    {\r\n" + "      \"use\": \"home\",\r\n" + "      \"line\": [\r\n"
				+ "        \"534 Erewhon St\"\r\n" + "      ],\r\n" + "      \"city\": \"PleasantVille\",\r\n"
				+ "      \"state\": \"Vic\",\r\n" + "      \"postalCode\": \"3999\"\r\n" + "    }\r\n" + "  ],\r\n"
				+ "  \"active\": true\r\n" + "}";

		MvcResult mvcresult = mvc
				.perform(MockMvcRequestBuilders.get("/patient/Dummy").accept(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcresult.getResponse().getContentAsString();
		Bundle bundle = (Bundle) FhirParser.parseResource(resultContent);
		int statusRetrieve = mvcresult.getResponse().getStatus();

		if (bundle.getTotal() < 1) {
			MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/patient")
					.contentType("application/fhir+json;q=1.0, application/json+fhir;q=0.9").content(personJson))
					.andReturn();

			int statusCreate = mvcResult.getResponse().getStatus();
			assertEquals(201, statusCreate);
		} else {
			assertEquals(200, statusRetrieve);
		}
	}

	@Test
	@WithMockUser
	public void extractPatientTest() throws Exception {
		MvcResult mvcresult = mvc
				.perform(MockMvcRequestBuilders.get("/patient/Dummy").accept(MediaType.APPLICATION_JSON)).andReturn();
		String resultContent = mvcresult.getResponse().getContentAsString();
		Bundle bundle = (Bundle) FhirParser.parseResource(resultContent);
		assertTrue(bundle.getTotal() > 0);

	}

}
