package com.possible.imisconnect.test.service;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.possible.imisconnect.web.OmrsFhirConsumer;

@RunWith(SpringRunner.class)
@WebMvcTest(OmrsFhirConsumer.class)
public class TestOmrsFhirController {

	@Autowired
	private MockMvc mvc;

	@Test
	public void getPatient() throws Exception {
		mvc.perform(get("/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}
