package com.travix.medusa.busyflights.domain.crazyair;

import java.io.Serializable;

import lombok.Data;

@Data
public class CrazyAirRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7509601276825681990L;
	private String origin;
	private String destination;
	private String departureDate;
	private String returnDate;
	private int passengerCount;

}
