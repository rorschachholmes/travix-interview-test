package com.travix.medusa.busyflights.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.travix.medusa.busyflights.api.validation.RequestValidator;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.search.aggregator.FlightsSearchSortByFareAggregator;
import com.travix.medusa.busyflights.domain.busyflights.search.aggregator.SearchAggregatorResolver;

import reactor.core.publisher.Flux;

/**
 * 
 * Controller class to handle requests for busyflights flights search
 * 
 */
@RestController
@PropertySource("classpath:busyflights-service.properties")
public class BusyFlightsSearchController {

	@Autowired
	private SearchAggregatorResolver aggregatorResolver;

	@Autowired
	private RequestValidator requestValidator;

	@Value("${busyflights.api.message.noResultsFound}")
	private String noResultsFoundMessage;
	@Value("${busyflights.api.message.errorOccurred}")
	private String errorOccurredMessage;

	/**
	 * API Method to expost flight search API
	 * 
	 * This api can consume both form input from an UI and JSON input from a
	 * independent API tester and product both application/json or
	 * application/stream+json(for reactive clients)
	 * 
	 * @param request
	 * @return {@link Flux} of {@link BusyFlightsResponse} emitting data in a
	 *         non-blocking way
	 */
	@PostMapping(value = "/flights/search", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_FORM_URLENCODED_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_STREAM_JSON_VALUE })
	public Flux<BusyFlightsResponse> searchFlightsSortByFare(@Valid @RequestBody final BusyFlightsRequest request) {
		return Flux.just(request).doOnNext(requestValidator::validateRequest).flatMap(
				aggregatorResolver.resolveAggregator(FlightsSearchSortByFareAggregator.class)::aggregateSearchResults)
				.onErrorResume(Throwable.class,
						e -> Flux.error(
								new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, errorOccurredMessage)))
				.switchIfEmpty(Flux.error(new ResponseStatusException(HttpStatus.NOT_FOUND, noResultsFoundMessage)));

	}

}
