package com.travix.medusa.busyflights.api.error;

import java.io.Serializable;
import java.util.Set;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * Custom API Error model class to hold data for errors
 *
 */
@Data
public class SearchResultApiError implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 5779303341080819124L;
	private HttpStatus errorCode;
	private Set<String> errorMessages;

}
