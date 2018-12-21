package com.travix.medusa.busyflights.domain.busyflights.search.aggregator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.busyflights.search.converter.SearchResultConverterResolver;
import com.travix.medusa.busyflights.domain.busyflights.search.converter.SupplierSearchResultsConverter;
import com.travix.medusa.busyflights.domain.busyflights.search.sorting.SortByFareAscendingStrategy;
import com.travix.medusa.busyflights.domain.busyflights.search.sorting.SortingStrategyResolver;

import reactor.core.publisher.Flux;

/**
 * 
 * A concrete implementation of {@link FlightsSearchAggregator} which aggregates
 * search results from different suppliers sorted by fare in ascending order as
 * provided by the sorting strategy
 *
 */
@Service
public class FlightsSearchSortByFareAggregator implements FlightsSearchAggregator {

	@Autowired
	private SearchResultConverterResolver searchResultConverterResolver;

	@Autowired
	private SortingStrategyResolver sortingStrategyResolver;

	/**
	 * Aggregation logic by combining results from CrazyAir and ToughJet and then
	 * sorting the accumulated results by fare low to high.
	 * 
	 * In the future when a new supplier is added,
	 * 
	 * <pre>
	 * 1. A new class implementing {@link SupplierSearchResultsConverter} needs to be created 
	 * 2. The new supplier results can then easily be added to the Flux.merge() method
	 * like CrazyAir and ToughJet.
	 * </pre>
	 * 
	 * 
	 */
	@Override
	public Flux<BusyFlightsResponse> aggregateSearchResults(final BusyFlightsRequest request) {
		return Flux
				.merge(getResultsFromSupplier(searchResultConverterResolver, request, Supplier.CRAZYAIR),
						getResultsFromSupplier(searchResultConverterResolver, request, Supplier.TOUGHJET))
				.sort(sortingStrategyResolver.resolveSortingStrategy(SortByFareAscendingStrategy.class));
	}

	private Flux<BusyFlightsResponse> getResultsFromSupplier(final SearchResultConverterResolver converterResolver,
			final BusyFlightsRequest request, final Supplier supplier) {
		return converterResolver.resolveConverter(supplier).convertSupplierResultsFromRequest(request);
	}

}
