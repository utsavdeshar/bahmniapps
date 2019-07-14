package org.bahmni.insurance.web;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.bahmni.insurance.model.ErrorJson;
import org.bahmni.insurance.utils.InsuranceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class CustomErrorController implements ErrorController {

	private static final String PATH = "/error";

	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	public ModelAndView handleError(WebRequest request, HttpServletResponse response) throws JsonProcessingException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error-page");
		ErrorJson error = new ErrorJson(response.getStatus(), getErrorAttributes(request, false));

		modelAndView.addObject("error", InsuranceUtils.mapToJson(error));
		return modelAndView;
	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
		return errorAttributes.getErrorAttributes(request, includeStackTrace);
	}
}