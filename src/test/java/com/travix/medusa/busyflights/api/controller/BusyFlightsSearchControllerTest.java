package com.travix.medusa.busyflights.api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.travix.medusa.busyflights.api.error.SearchRequestValidationException;
import com.travix.medusa.busyflights.api.validation.RequestValidator;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.search.aggregator.FlightsSearchSortByFareAggregator;
import com.travix.medusa.busyflights.domain.busyflights.search.aggregator.SearchAggregatorResolver;
import com.travix.medusa.busyflights.utility.StubsFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class BusyFlightsSearchControllerTest {

	@Mock
	private SearchAggregatorResolver searchAggregatorResolver;

	@Mock
	private FlightsSearchSortByFareAggregator flightsSearchSortByFareAggregator;

	@Mock
	private RequestValidator requestValidator;

	@InjectMocks
	private BusyFlightsSearchController busyFlightsSearchController;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testSearchFlightsSortByFare_When_HavingResults() {
		doReturn(flightsSearchSortByFareAggregator).when(searchAggregatorResolver)
				.resolveAggregator(FlightsSearchSortByFareAggregator.class);
		when(flightsSearchSortByFareAggregator.aggregateSearchResults(any(BusyFlightsRequest.class))).thenReturn(
				Flux.fromIterable(StubsFactory.getStubAggregateBusyFlightsResponseSortByFareAscendingAsList()));
		doNothing().when(requestValidator).validateRequest(any(BusyFlightsRequest.class));
		StepVerifier
				.create(busyFlightsSearchController.searchFlightsSortByFare(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextMatches(Objects::nonNull).expectNextMatches(Objects::nonNull)
				.expectNextMatches(Objects::nonNull).expectNextMatches(Objects::nonNull)
				.expectNextMatches(Objects::nonNull).expectNextMatches(Objects::nonNull)
				.expectNextMatches(Objects::nonNull).expectNextMatches(Objects::nonNull)
				.expectNextMatches(Objects::nonNull).expectNextMatches(Objects::nonNull).expectComplete().verify();

	}

	@Test
	public void testSearchFlightsSortByFare_When_NoResults() {
		doReturn(flightsSearchSortByFareAggregator).when(searchAggregatorResolver)
				.resolveAggregator(FlightsSearchSortByFareAggregator.class);
		when(flightsSearchSortByFareAggregator.aggregateSearchResults(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.empty());
		doNothing().when(requestValidator).validateRequest(any(BusyFlightsRequest.class));
		StepVerifier
				.create(busyFlightsSearchController.searchFlightsSortByFare(StubsFactory.getStubBusyFlightsRequest()))
				.expectErrorMatches(e -> e.getClass().equals(ResponseStatusException.class)
						&& ((ResponseStatusException) e).getStatus().equals(HttpStatus.NOT_FOUND))
				.verify();

	}

	@Test
	public void testSearchFlightsSortByFare_When_ValidationError() {
		doReturn(flightsSearchSortByFareAggregator).when(searchAggregatorResolver)
				.resolveAggregator(FlightsSearchSortByFareAggregator.class);
		doThrow(new SearchRequestValidationException("Validation Error")).when(requestValidator)
				.validateRequest(any(BusyFlightsRequest.class));
		StepVerifier
				.create(busyFlightsSearchController.searchFlightsSortByFare(StubsFactory.getStubBusyFlightsRequest()))
				.expectError(RuntimeException.class).verify();

	}

}
