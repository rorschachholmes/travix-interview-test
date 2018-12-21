package com.travix.medusa.busyflights.domain.toughjet;

import java.io.Serializable;

import lombok.Data;

@Data
public class ToughJetResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8149915975561683712L;

	private String carrier;
	private double basePrice;
	private double tax;
	private double discount;
	private String departureAirportName;
	private String arrivalAirportName;
	private String outboundDateTime;
	private String inboundDateTime;

}
