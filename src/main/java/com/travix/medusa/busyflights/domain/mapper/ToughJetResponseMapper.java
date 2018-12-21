package com.travix.medusa.busyflights.domain.mapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.utility.FareCalculationUtils;

/**
 * 
 * Mapper class definition to map a {@link ToughJetResponse} to a
 * {@link BusyFlightsResponse}
 *
 */

@Component
public class ToughJetResponseMapper implements Function<ToughJetResponse, BusyFlightsResponse> {

	@Override
	public BusyFlightsResponse apply(final ToughJetResponse toughJetResponse) {
		final BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();
		if (null != toughJetResponse) {
			busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
			busyFlightsResponse.setSupplier(Supplier.TOUGHJET.getSupplierName());
			busyFlightsResponse.setArrivalDate(Instant.parse(toughJetResponse.getInboundDateTime())
					.atZone(ZoneOffset.UTC).toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
			busyFlightsResponse.setDepartureDate(Instant.parse(toughJetResponse.getOutboundDateTime())
					.atZone(ZoneOffset.UTC).toLocalDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
			busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
			busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
			busyFlightsResponse.setFare(BigDecimal
					.valueOf(FareCalculationUtils.calculateFareByDiscount(toughJetResponse.getBasePrice(),
							toughJetResponse.getDiscount()))
					.add(BigDecimal.valueOf(toughJetResponse.getTax())).setScale(2, RoundingMode.FLOOR).doubleValue());
		}
		return busyFlightsResponse;
	}

}
