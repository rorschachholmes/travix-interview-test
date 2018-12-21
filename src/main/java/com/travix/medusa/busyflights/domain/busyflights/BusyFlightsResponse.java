package com.travix.medusa.busyflights.domain.busyflights;

import java.io.Serializable;

import lombok.Data;

@Data
public class BusyFlightsResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1980622113701966054L;
	private String airline;
	private String supplier;
	private double fare;
	private String departureAirportCode;
	private String destinationAirportCode;
	private String departureDate;
	private String arrivalDate;

}
