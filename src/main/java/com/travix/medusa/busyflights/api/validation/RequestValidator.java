package com.travix.medusa.busyflights.api.validation;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.api.error.SearchRequestValidationException;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;

/**
 * Validation class to provide extensive validation on the busyflights search
 * request.
 * 
 *
 */
@Component
@PropertySource("classpath:busyflights-service.properties")
public class RequestValidator {

	@Value("${busyflights.request.validation.message.invalidDate}")
	private String invalidDateMessage;

	@Value("${busyflights.request.validation.message.invalidDateRange}")
	private String invalidDateRangeMessage;

	@Value("${busyflights.request.validation.message.invalidIATACode}")
	private String invalidIATACodeMessage;

	private static final String IATA_PATTERN = "^[A-Z]{3}$";

	public void validateRequest(final BusyFlightsRequest busyFlightsRequest) {
		final String departureDate = busyFlightsRequest.getDepartureDate();
		final String returnDate = busyFlightsRequest.getReturnDate();
		final String origin = busyFlightsRequest.getOrigin();
		final String destination = busyFlightsRequest.getDestination();

		if (!isValidDate(departureDate)) {
			throw new SearchRequestValidationException(MessageFormat.format(invalidDateMessage, "Departure Date"));
		}

		if (!isValidDate(returnDate)) {
			throw new SearchRequestValidationException(MessageFormat.format(invalidDateMessage, "Return Date"));
		}

		if (!isDatesWithinValidRange(departureDate, returnDate)) {
			throw new SearchRequestValidationException(
					MessageFormat.format(invalidDateRangeMessage, "Departure Date", "Return Date"));
		}

		if (!isValidIATACode(origin)) {
			throw new SearchRequestValidationException(
					MessageFormat.format(invalidIATACodeMessage, "Origin airport code"));
		}

		if (!isValidIATACode(destination)) {
			throw new SearchRequestValidationException(
					MessageFormat.format(invalidIATACodeMessage, "Destination airport code"));
		}
	}

	private boolean isValidDate(final String date) {
		boolean isValidDate = true;
		try {
			LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (final DateTimeParseException ex) {
			isValidDate = false;
		}
		return isValidDate;
	}

	private boolean isDatesWithinValidRange(final String departureDate, final String returnDate) {

		return getDateStringInLocalDate(departureDate).isBefore(getDateStringInLocalDate(returnDate))
				&& getDateStringInLocalDate(departureDate).isAfter(getDateStringInLocalDate(LocalDate.now().toString()))
				&& !getDateStringInLocalDate(departureDate).isEqual(getDateStringInLocalDate(returnDate));

	}

	private LocalDate getDateStringInLocalDate(final String date) {
		return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
	}

	private boolean isValidIATACode(final String airportCode) {
		return airportCode.matches(IATA_PATTERN);
	}

}
