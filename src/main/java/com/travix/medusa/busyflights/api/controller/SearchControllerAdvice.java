package com.travix.medusa.busyflights.api.controller;

import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import com.travix.medusa.busyflights.api.error.SearchRequestValidationException;
import com.travix.medusa.busyflights.api.error.SearchResultApiError;

import reactor.core.publisher.Mono;

/**
 * Controller Advice class to handle errors at the
 * {@link BusyFlightsSearchController} and convert them to a more user friendly
 * format to the client.
 * 
 *
 */
@ControllerAdvice(assignableTypes = BusyFlightsSearchController.class)
public class SearchControllerAdvice {

	@ExceptionHandler(WebExchangeBindException.class)
	public Mono<ResponseEntity<SearchResultApiError>> badRequestException(WebExchangeBindException e) {
		final SearchResultApiError apiError = new SearchResultApiError();
		apiError.setErrorMessages(e.getBindingResult().getFieldErrors().stream().map(this::formatErrorString)
				.collect(Collectors.toSet()));
		apiError.setErrorCode(e.getStatus());
		return Mono.just(new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(SearchRequestValidationException.class)
	public Mono<ResponseEntity<SearchResultApiError>> requestValidationException(SearchRequestValidationException e) {
		final SearchResultApiError apiError = new SearchResultApiError();
		apiError.setErrorMessages(new HashSet<>(Collections.singletonList(e.getMessage())));
		apiError.setErrorCode(HttpStatus.BAD_REQUEST);
		return Mono.just(new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(ResponseStatusException.class)
	public Mono<ResponseEntity<SearchResultApiError>> responseException(ResponseStatusException e) {
		final SearchResultApiError apiError = new SearchResultApiError();
		apiError.setErrorMessages(new HashSet<>(Collections.singletonList(e.getMessage())));
		apiError.setErrorCode(e.getStatus());
		return Mono.just(new ResponseEntity<>(apiError, e.getStatus()));
	}

	private String formatErrorString(final FieldError error) {
		return "{" + error.getField() + "} " + error.getDefaultMessage();

	}

}
