package com.travix.medusa.busyflights.domain.crazyair;

import java.io.Serializable;

import lombok.Data;

@Data
public class CrazyAirResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1887390832901330511L;
	private String airline;
	private double price;
	private String cabinclass;
	private String departureAirportCode;
	private String destinationAirportCode;
	private String departureDate;
	private String arrivalDate;

}
