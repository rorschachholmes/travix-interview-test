package com.travix.medusa.busyflights.api.validation;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.travix.medusa.busyflights.api.error.SearchRequestValidationException;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.utility.StubsFactory;

public class RequestValidatorTest {

	private RequestValidator requestValidator;

	@BeforeEach
	public void setUp() throws Exception {
		requestValidator = new RequestValidator();
		ReflectionTestUtils.setField(requestValidator, "invalidDateMessage",
				"{0} is not a valid date in ISO_LOCAL_DATE format. A valid date is '2018-12-31'");
		ReflectionTestUtils.setField(requestValidator, "invalidDateRangeMessage",
				"{0} must be a future date from now and is always before the {1}");
		ReflectionTestUtils.setField(requestValidator, "invalidIATACodeMessage",
				"{0} must be a valid IATA code. Valid IATA codes are [LHR, AMS]");
	}

	@Test
	public void testValidateRequest_Valid_Request() {
		final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
		requestValidator.validateRequest(request);
	}

	@Test
	public void testValidateRequest_Invalid_DepartureDate() {
		try {
			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setDepartureDate("20183112");
			requestValidator.validateRequest(request);
		} catch (final SearchRequestValidationException ex) {

			assertEquals("Departure Date is not a valid date in ISO_LOCAL_DATE format. A valid date is 2018-12-31",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_Invalid_ReturnDate() {
		try {
			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setReturnDate("20183112");
			requestValidator.validateRequest(request);

		} catch (final SearchRequestValidationException ex) {
			assertEquals("Return Date is not a valid date in ISO_LOCAL_DATE format. A valid date is 2018-12-31",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_DepartureDateAfterReturnDate() {
		try {
			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setDepartureDate("2018-12-31");
			request.setReturnDate("2018-12-21");
			requestValidator.validateRequest(request);
		} catch (final SearchRequestValidationException ex) {
			assertEquals("Departure Date must be a future date from now and is always before the Return Date",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_DepartureDateBeforeTodaysDate() {
		try {
			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setDepartureDate("2018-12-11");
			requestValidator.validateRequest(request);
		} catch (final SearchRequestValidationException ex) {
			assertEquals("Departure Date must be a future date from now and is always before the Return Date",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_DepartureDateEqualsReturnDate() {
		try {

			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setDepartureDate(request.getReturnDate());
			requestValidator.validateRequest(request);
		} catch (final SearchRequestValidationException ex) {
			assertEquals("Departure Date must be a future date from now and is always before the Return Date",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_Invalid_OriginCode() {
		try {
			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setOrigin("ABCDE");
			requestValidator.validateRequest(request);

		} catch (final SearchRequestValidationException ex) {
			assertEquals("Origin airport code must be a valid IATA code. Valid IATA codes are [LHR, AMS]",
					ex.getMessage());
		}
	}

	@Test
	public void testValidateRequest_Invalid_DestinationCode() {
		try {

			final BusyFlightsRequest request = StubsFactory.getStubBusyFlightsRequest();
			request.setDestination("12345");
			requestValidator.validateRequest(request);
		} catch (final SearchRequestValidationException ex) {
			assertEquals("Destination airport code must be a valid IATA code. Valid IATA codes are [LHR, AMS]",
					ex.getMessage());
		}
	}

}
