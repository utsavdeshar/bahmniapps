package org.bahmni.insurance.web;

import static org.apache.log4j.Logger.getLogger;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bahmni.insurance.exception.ErrorJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
public class CustomWebErrorController implements ErrorController {

	private static final String PATH = "/error";

	private final Logger logger = getLogger(CustomWebErrorController.class);
	
	@Value("${imisconnect.errortrace.include}")
	public boolean includeErrorTrace;

	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	public ErrorJson handleError(WebRequest request, HttpServletResponse response) {
		ErrorJson error = new ErrorJson(response.getStatus(), getErrorAttributes(request, includeErrorTrace));
		logger.error(error.getError() + " " + error.getMessage());
		return error;

	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
		return errorAttributes.getErrorAttributes(request, includeStackTrace);
	}
}