package com.travix.medusa.busyflights.domain.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

/**
 * 
 * Mapper class definition to map a {@link CrazyAirResponse} to a
 * {@link BusyFlightsResponse}
 *
 */
@Component
public class CrazyAirResponseMapper implements Function<CrazyAirResponse, BusyFlightsResponse> {

	@Override
	public BusyFlightsResponse apply(final CrazyAirResponse crazyAirResponse) {
		final BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
		if (null != crazyAirResponse) {
			busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
			busyFlightsResponse.setSupplier(Supplier.CRAZYAIR.getSupplierName());
			busyFlightsResponse.setArrivalDate(
					LocalDateTime.parse(crazyAirResponse.getArrivalDate()).format(DateTimeFormatter.ISO_DATE_TIME));
			busyFlightsResponse.setDepartureDate(
					LocalDateTime.parse(crazyAirResponse.getDepartureDate()).format(DateTimeFormatter.ISO_DATE_TIME));
			busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
			busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
			busyFlightsResponse.setFare(
					BigDecimal.valueOf(crazyAirResponse.getPrice()).setScale(2, RoundingMode.FLOOR).doubleValue());
		}
		return busyFlightsResponse;
	}

}
