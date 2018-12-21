package com.travix.medusa.busyflights.domain.busyflights.search.aggregator;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

import reactor.core.publisher.Flux;

/**
 * 
 * Interface exposing contract for flight search aggregators
 * 
 * This is made because in the future any different type of aggregator can
 * implement this interface to aggregate results in its own specific format.
 *
 */
public interface FlightsSearchAggregator {

	Flux<BusyFlightsResponse> aggregateSearchResults(final BusyFlightsRequest request);

}
