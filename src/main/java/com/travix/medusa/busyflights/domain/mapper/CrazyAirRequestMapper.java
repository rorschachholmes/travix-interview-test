package com.travix.medusa.busyflights.domain.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;

/**
 * 
 * Mapper class definition to map a {@link BusyFlightsRequest} to a
 * {@link CrazyAirRequest}
 *
 */
@Component
public class CrazyAirRequestMapper implements Function<BusyFlightsRequest, CrazyAirRequest> {

	@Override
	public CrazyAirRequest apply(final BusyFlightsRequest busyFlightsRequest) {
		final CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
		if (null != busyFlightsRequest) {
			crazyAirRequest.setOrigin(busyFlightsRequest.getOrigin());
			crazyAirRequest.setDestination(busyFlightsRequest.getDestination());
			crazyAirRequest.setPassengerCount(busyFlightsRequest.getNumberOfPassengers());
			crazyAirRequest.setDepartureDate(busyFlightsRequest.getDepartureDate());
			crazyAirRequest.setReturnDate(busyFlightsRequest.getReturnDate());
		}
		return crazyAirRequest;

	}

}
