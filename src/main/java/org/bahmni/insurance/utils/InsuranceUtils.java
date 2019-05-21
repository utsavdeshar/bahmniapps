package org.bahmni.insurance.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

public class InsuranceUtils {
	public static final java.lang.String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	private static final Logger LOGGER = LoggerFactory.getLogger(InsuranceUtils.class);

	/**
	 * This method configures Gson. We need to use workaround for null dates.
	 * 
	 * @return definitive null safe Gson instance
	 */
	public static Gson createDefaultGson() {
		// Trick to get the DefaultDateTypeAdatpter instance
		// Create a first Gson instance
		Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

		// Get the date adapter
		TypeAdapter<Date> dateTypeAdapter = gson.getAdapter(Date.class);

		// Ensure the DateTypeAdapter is null safe
		TypeAdapter<Date> safeDateTypeAdapter = dateTypeAdapter.nullSafe();

		// Build the definitive safe Gson instance
		return new GsonBuilder().setDateFormat(DATE_FORMAT).registerTypeAdapter(Date.class, safeDateTypeAdapter)
				.create();
	}
}
