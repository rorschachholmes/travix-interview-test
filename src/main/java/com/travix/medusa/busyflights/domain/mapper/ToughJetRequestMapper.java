package com.travix.medusa.busyflights.domain.mapper;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;

/**
 * 
 * Mapper class definition to map a {@link BusyFlightsRequest} to a
 * {@link ToughJetRequest}
 *
 */
@Component
public class ToughJetRequestMapper implements Function<BusyFlightsRequest, ToughJetRequest> {

	@Override
	public ToughJetRequest apply(final BusyFlightsRequest busyFlightsRequest) {
		final ToughJetRequest toughJetRequest = new ToughJetRequest();
		if (null != busyFlightsRequest) {
			toughJetRequest.setFrom(busyFlightsRequest.getOrigin());
			toughJetRequest.setTo(busyFlightsRequest.getDestination());
			toughJetRequest.setNumberOfAdults(busyFlightsRequest.getNumberOfPassengers());
			toughJetRequest.setInboundDate(busyFlightsRequest.getReturnDate());
			toughJetRequest.setOutboundDate(busyFlightsRequest.getDepartureDate());
		}
		return toughJetRequest;
	}

}
