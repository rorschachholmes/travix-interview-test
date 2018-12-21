package com.travix.medusa.busyflights.domain.busyflights.search.sorting;

import java.util.Comparator;

import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

/**
 * 
 * Sorting strategy class definition to sort two {@link BusyFlightsResponse}
 * objects based on its fare
 *
 * Likewise, in the future for a different sorting strategy, a new class just
 * needs to be created implementing {@link Comparator} and providing the
 * specific implementation of the compare method.
 */
@Component
public class SortByFareAscendingStrategy implements Comparator<BusyFlightsResponse> {

	@Override
	public int compare(BusyFlightsResponse o1, BusyFlightsResponse o2) {
		return Double.valueOf(o1.getFare()).compareTo(Double.valueOf(o2.getFare()));
	}

}
