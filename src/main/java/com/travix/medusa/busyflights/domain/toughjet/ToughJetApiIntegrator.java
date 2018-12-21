package com.travix.medusa.busyflights.domain.toughjet;

import java.util.Collections;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.travix.medusa.busyflights.supplier.integrator.http.PostApiIntegrator;
import com.travix.medusa.busyflights.supplier.integrator.http.webclient.IntegratorWebClient;

import reactor.core.publisher.Flux;

/**
 * HTTP API integration class definition for ToughJet supplier.
 * 
 * Assumptions : ToughJet exposes a POST API to give flights search results.
 * 
 * Such an assumption has been made because with a GET request, the number of
 * query parameters might get large in the future if more params are added.
 * 
 *
 */
@Component
@PropertySource("classpath:busyflights-service.properties")
public class ToughJetApiIntegrator implements PostApiIntegrator<ToughJetRequest, ToughJetResponse> {

	@Autowired
	private IntegratorWebClient client;

	@Value("${busyflights.supplier.integration.url.toughjet}")
	private String toughJetBaseIntegrationUrl;

	@Override
	public Flux<ToughJetResponse> integratePostApi(@NotNull @Valid ToughJetRequest requestBody,
			List<String> pathVariables, List<String> uriPathSegments) {
		return client
				.exchangePost(toughJetBaseIntegrationUrl, requestBody, pathVariables, uriPathSegments,
						Collections.emptyMap(), ToughJetResponse.class)
				.onErrorResume(WebClientResponseException.class,
						e -> e.getStatusCode().is4xxClientError()
								? Flux.error(new RuntimeException("Error while calling supplier API"))
								: Flux.empty());
	}

}
