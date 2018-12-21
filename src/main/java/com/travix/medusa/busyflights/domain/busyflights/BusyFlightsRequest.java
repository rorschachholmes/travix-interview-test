package com.travix.medusa.busyflights.domain.busyflights;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusyFlightsRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8134284823850177649L;
	@NotNull
	@NotBlank
	private String origin;
	@NotNull
	@NotBlank
	private String destination;
	@NotNull
	@NotBlank
	private String departureDate;
	@NotNull
	@NotBlank
	private String returnDate;
	@Positive
	@Max(value = 4)
	private int numberOfPassengers;
}
