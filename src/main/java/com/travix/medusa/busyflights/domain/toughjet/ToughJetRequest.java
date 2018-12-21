package com.travix.medusa.busyflights.domain.toughjet;

import java.io.Serializable;

import lombok.Data;

@Data
public class ToughJetRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1964729701024020080L;
	private String from;
	private String to;
	private String outboundDate;
	private String inboundDate;
	private int numberOfAdults;
}
