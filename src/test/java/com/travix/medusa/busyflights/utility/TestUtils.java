package com.travix.medusa.busyflights.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class TestUtils {

	private TestUtils() {

	}

	public static <T> T convertJsonToObject(final String jsonFileName, final Class<T> clazz) {

		T jsonObject = null;
		InputStream is = null;
		try {
			is = TestUtils.class.getResourceAsStream("/data-stubs/" + jsonFileName);
			final ObjectMapper mapper = new ObjectMapper();
			jsonObject = (T) mapper.readValue(is, clazz);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return jsonObject;
	}

}
