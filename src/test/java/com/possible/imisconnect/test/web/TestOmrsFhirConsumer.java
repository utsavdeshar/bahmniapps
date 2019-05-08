package com.possible.imisconnect.test.web;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.possible.imisconnect.auth.AuthenticationFilter;
import com.possible.imisconnect.auth.OpenMRSAuthenticator;
import com.possible.imisconnect.web.RequestProcessor;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RequestProcessor.class)
public class TestOmrsFhirConsumer {

	@Mock
	@Autowired
	private OpenMRSAuthenticator authenticator;

	@Mock
	@Autowired
	private AuthenticationFilter authFilter;

	@Autowired
	private RequestMappingHandlerAdapter handlerAdapter;

	@Autowired
	private RequestMappingHandlerMapping handlerMapping;

	private InterceptorRegistry registry;

	private final MockHttpServletRequest mockRequest = new MockHttpServletRequest();

	private final MockHttpServletResponse mockResponse = new MockHttpServletResponse();

	public void setupInterceptor() throws Exception {
		HandlerExecutionChain handlerExecutionChain = handlerMapping.getHandler(mockRequest);
		HandlerInterceptor[] interceptors = handlerExecutionChain.getInterceptors();
		interceptors[0] = Mockito.mock(AuthenticationFilter.class);
		Mockito.when(interceptors[0].preHandle(mockRequest, mockResponse, handlerExecutionChain.getHandler()))
				.thenReturn(true);

		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.preHandle(mockRequest, mockResponse, handlerExecutionChain.getHandler());
		}

		ModelAndView mav = handlerAdapter.handle(mockRequest, mockResponse, handlerExecutionChain.getHandler());

		for (HandlerInterceptor interceptor : interceptors) {
			interceptor.postHandle(mockRequest, mockResponse, handlerExecutionChain.getHandler(), mav);
		}

	}

	@Test
	public void testGetPatient() throws Exception {
		mockRequest.setRequestURI("/patient");
		mockRequest.setMethod("GET");

		setupInterceptor();

		assertEquals(200, mockResponse.getStatus());

		// mvc.perform(get("/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}
	
	@Test
	public void testGetOrderCost() throws Exception {
		mockRequest.setRequestURI("/odercost");
		mockRequest.setMethod("GET");

		setupInterceptor();

		assertEquals(200, mockResponse.getStatus());

		// mvc.perform(get("/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	@Test
	public void testAuth() throws Exception {

		mockRequest.setRequestURI("/auth");
		mockRequest.setMethod("GET");

		setupInterceptor();

		assertEquals(200, mockResponse.getStatus());

		// mvc.perform(get("/patient").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

}
