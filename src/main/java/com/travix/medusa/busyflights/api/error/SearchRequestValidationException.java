package com.travix.medusa.busyflights.api.error;

import org.springframework.stereotype.Component;

/**
 * Exception class denoting exception on validation failure of search request
 * 
 */
@Component
public class SearchRequestValidationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6638707425503837265L;

	public SearchRequestValidationException() {
		super();
	}

	public SearchRequestValidationException(final String validationErrorMessage) {
		super(validationErrorMessage);
	}

}
