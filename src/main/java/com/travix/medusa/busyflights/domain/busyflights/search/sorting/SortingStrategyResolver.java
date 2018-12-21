package com.travix.medusa.busyflights.domain.busyflights.search.sorting;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * Resolver class definition to resolve a sorting strategy at runtime
 * 
 *
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SortingStrategyResolver {

	@Autowired
	private List<Comparator> strategies;

	private static final Map<String, Comparator> strategyClassMap = new ConcurrentHashMap<>();

	@PostConstruct
	void init() {
		strategies.forEach(strategy -> strategyClassMap.put(strategy.getClass().getName(), strategy));
	}

	/**
	 * Resolver method to resolve a sorting strategy
	 * 
	 * @param sortingStrategyClass the concrete implementation class needed at
	 *                             runtime
	 * @return {@link Comparator} representing the sorting strategy
	 */
	public <T> Comparator<T> resolveSortingStrategy(final Class<? extends Comparator<T>> sortingStrategyClass) {
		return strategyClassMap.get(sortingStrategyClass.getName());
	}
}
