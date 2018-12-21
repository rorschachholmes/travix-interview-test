package com.travix.medusa.busyflights.domain.busyflights.search.converter;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;

import reactor.core.publisher.Flux;

/**
 * This is an uniform interface definition which exposes a contract to accept a
 * {@link BusyFlightsRequest} and produce a {@link Flux} emitting
 * {@link BusyFlightsResponse}
 * 
 * Concrete implementations of this interface evaluates search results from a
 * specific supplier and converts them to {@link BusyFlightsResponse}.
 * 
 * This is made, such that in future, results from a new supplier system can be
 * easily converted by just implementing this interface
 * 
 * For the busyflights domain , this interface can be treated as an
 * anti-corruption layer.
 *
 */
public interface SupplierSearchResultsConverter {

	Flux<BusyFlightsResponse> convertSupplierResultsFromRequest(final BusyFlightsRequest request);

	Supplier getSupplierType();
}
