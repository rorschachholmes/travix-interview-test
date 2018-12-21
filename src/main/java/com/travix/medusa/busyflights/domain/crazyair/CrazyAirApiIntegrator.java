package com.travix.medusa.busyflights.domain.crazyair;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.travix.medusa.busyflights.supplier.integrator.http.PostApiIntegrator;
import com.travix.medusa.busyflights.supplier.integrator.http.webclient.IntegratorWebClient;

import reactor.core.publisher.Flux;

/**
 * HTTP API integration class definition for CrazyAir supplier.
 * 
 * Assumptions : CrazyAir exposes a POST API to give flights search results.
 * 
 * Such an assumption has been made because with a GET request, the number of query
 * parameters might get large in the future if more params are added.
 * 
 *
 */
@Component
@PropertySource("classpath:busyflights-service.properties")
public class CrazyAirApiIntegrator implements PostApiIntegrator<CrazyAirRequest, CrazyAirResponse> {

	@Autowired
	private IntegratorWebClient client;

	@Value("${busyflights.supplier.integration.url.crazyair}")
	private String crazyAirBaseIntegrationUrl;

	@Override
	public Flux<CrazyAirResponse> integratePostApi(final CrazyAirRequest requestBody, final List<String> pathVariables,
			final List<String> uriPathSegments) {
		return client
				.exchangePost(crazyAirBaseIntegrationUrl, requestBody, pathVariables, uriPathSegments,
						Collections.emptyMap(), CrazyAirResponse.class)
				.onErrorResume(WebClientResponseException.class,
						e -> e.getStatusCode().is4xxClientError()
								? Flux.error(new RuntimeException("Error while calling supplier API"))
								: Flux.empty());
	}

}
