package com.travix.medusa.busyflights.domain.busyflights.search.aggregator;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.search.converter.CrazyAirSearchResultsConverter;
import com.travix.medusa.busyflights.domain.busyflights.search.converter.SearchResultConverterResolver;
import com.travix.medusa.busyflights.domain.busyflights.search.converter.ToughJetSearchResultsConverter;
import com.travix.medusa.busyflights.domain.busyflights.search.sorting.SortByFareAscendingStrategy;
import com.travix.medusa.busyflights.domain.busyflights.search.sorting.SortingStrategyResolver;
import com.travix.medusa.busyflights.utility.StubsFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class FlightsSearchSortByFareAggregatorTest {

	@Mock
	private SearchResultConverterResolver searchResultConverterResolver;

	@Mock
	private CrazyAirSearchResultsConverter crazyAirSearchResultsConverter;

	@Mock
	private ToughJetSearchResultsConverter toughJetSearchResultsConverter;

	@Mock
	private SortingStrategyResolver sortingStrategyResolver;

	@Mock
	private SortByFareAscendingStrategy sortByFareAscendingStrategy;

	@InjectMocks
	private FlightsSearchSortByFareAggregator flightsSearchSortByFareAggregator;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		doReturn(crazyAirSearchResultsConverter).when(searchResultConverterResolver).resolveConverter(Supplier.CRAZYAIR);
		doReturn(toughJetSearchResultsConverter).when(searchResultConverterResolver).resolveConverter(Supplier.TOUGHJET);
		doReturn(sortByFareAscendingStrategy).when(sortingStrategyResolver)
				.resolveSortingStrategy(SortByFareAscendingStrategy.class);

	}

	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_BothHavingResults() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromCrazyAirAsList()));
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromToughJetAsList()));
		doCallRealMethod().when(sortByFareAscendingStrategy).compare(any(BusyFlightsResponse.class),
				any(BusyFlightsResponse.class));
		StepVerifier
				.create(flightsSearchSortByFareAggregator
						.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextMatches(response -> 25.45 == response.getFare())
				.expectNextMatches(response -> 25.56 == response.getFare())
				.expectNextMatches(response -> 25.68 == response.getFare())
				.expectNextMatches(response -> 27.97 == response.getFare())
				.expectNextMatches(response -> 30.34 == response.getFare())
				.expectNextMatches(response -> 30.4 == response.getFare())
				.expectNextMatches(response -> 45.34 == response.getFare())
				.expectNextMatches(response -> 50.12 == response.getFare())
				.expectNextMatches(response -> 50.34 == response.getFare())
				.expectNextMatches(response -> 52.01 == response.getFare()).expectComplete().verify();

	}

	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_ToughJetNoResults() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromCrazyAirAsList()));
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.empty());
		doCallRealMethod().when(sortByFareAscendingStrategy).compare(any(BusyFlightsResponse.class),
				any(BusyFlightsResponse.class));
		StepVerifier
				.create(flightsSearchSortByFareAggregator
						.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextMatches(response -> 25.45 == response.getFare())
				.expectNextMatches(response -> 25.68 == response.getFare())
				.expectNextMatches(response -> 30.34 == response.getFare())
				.expectNextMatches(response -> 50.12 == response.getFare())
				.expectNextMatches(response -> 50.34 == response.getFare()).expectComplete().verify();

	}

	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_CrazyAirNoResults() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.empty());
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromToughJetAsList()));
		doCallRealMethod().when(sortByFareAscendingStrategy).compare(any(BusyFlightsResponse.class),
				any(BusyFlightsResponse.class));
		StepVerifier
				.create(flightsSearchSortByFareAggregator
						.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextMatches(response -> 25.56 == response.getFare())
				.expectNextMatches(response -> 27.97 == response.getFare())
				.expectNextMatches(response -> 30.4 == response.getFare())
				.expectNextMatches(response -> 45.34 == response.getFare())
				.expectNextMatches(response -> 52.01 == response.getFare()).expectComplete().verify();

	}

	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_BothHavingNoResults() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.empty());
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.empty());
		StepVerifier.create(
				flightsSearchSortByFareAggregator.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectComplete().verify();

	}

	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_CrazyAirError() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.error(new RuntimeException()));
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromToughJetAsList()));
		StepVerifier
				.create(flightsSearchSortByFareAggregator
						.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectError(RuntimeException.class).verify();

	}
	
	@Test
	public void testAggregateSearchResults_FromCrazyAirToughJet_SortByFare_ToughJetError() {
		when(crazyAirSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubBusyFlightsResponseFromCrazyAirAsList()));
		when(toughJetSearchResultsConverter.convertSupplierResultsFromRequest(any(BusyFlightsRequest.class)))
				.thenReturn(Flux.error(new RuntimeException()));
		StepVerifier
				.create(flightsSearchSortByFareAggregator
						.aggregateSearchResults(StubsFactory.getStubBusyFlightsRequest()))
				.expectError(RuntimeException.class).verify();

	}

}
