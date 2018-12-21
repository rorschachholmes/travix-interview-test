package com.travix.medusa.busyflights.supplier.integrator.http.webclient;

import java.net.URI;
import java.util.Map;
import java.util.function.Function;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import reactor.core.publisher.Flux;

/**
 * 
 * Web Client Template class definition to define a format for executing HTTP
 * request requests with {@link WebClient}
 * 
 * So far this class only exposes POST methods, but in the future if need be new
 * methods might be added for GET,PUT,PATCH,DELETE.
 *
 */
@Component
public class WebClientTemplate {

	public <T> Flux<T> executeGet(final String baseIntegrationUrl, final Function<UriBuilder, URI> uriFunction,
			@NotNull @Valid final Map<String, String> additionalHeaders, final Class<T> responseClass) {

		return WebClient.builder().baseUrl(baseIntegrationUrl).build().get().uri(uriFunction)
				.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON)
				.header(WebClientConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.headers(headers -> setHttpHeaders(headers, additionalHeaders)).retrieve().bodyToFlux(responseClass);

	}

	public <R, T> Flux<T> executePost(final String baseIntegrationUrl, final Function<UriBuilder, URI> uriFunction,
			@NotNull @Valid final Map<String, String> additionalHeaders, final R requestBody,
			final Class<T> responseClass) {

		return WebClient.builder().baseUrl(baseIntegrationUrl).build().post().uri(uriFunction)
				.body(BodyInserters.fromObject(requestBody))
				.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_STREAM_JSON)
				.header(WebClientConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.headers(headers -> setHttpHeaders(headers, additionalHeaders)).retrieve().bodyToFlux(responseClass);

	}

	private void setHttpHeaders(final HttpHeaders httpHeaders, final Map<String, String> additionalHeaders) {
		if (!CollectionUtils.isEmpty(additionalHeaders)) {
			httpHeaders.setAll(additionalHeaders);
		}
	}

}
