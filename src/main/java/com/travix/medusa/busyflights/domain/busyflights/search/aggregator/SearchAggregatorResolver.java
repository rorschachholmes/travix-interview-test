package com.travix.medusa.busyflights.domain.busyflights.search.aggregator;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * Resolver class definition to resolve a {@link FlightsSearchAggregator} at
 * runtime
 *
 */
@Component
public class SearchAggregatorResolver {

	@Autowired
	private List<FlightsSearchAggregator> aggregators;

	private static final Map<String, FlightsSearchAggregator> aggregatorClassMap = new ConcurrentHashMap<>();

	@PostConstruct
	void init() {
		aggregators.forEach(aggregator -> aggregatorClassMap.put(aggregator.getClass().getName(), aggregator));
	}

	/**
	 * Resolver method to resolve a {@link FlightsSearchAggregator}
	 * 
	 * @param aggregatorClass the concrete implementation class needed at runtime
	 * @return {@link FlightsSearchAggregator}
	 */
	public FlightsSearchAggregator resolveAggregator(final Class<? extends FlightsSearchAggregator> aggregatorClass) {
		return aggregatorClassMap.get(aggregatorClass.getName());
	}
}
