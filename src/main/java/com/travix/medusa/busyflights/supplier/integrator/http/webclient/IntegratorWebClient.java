package com.travix.medusa.busyflights.supplier.integrator.http.webclient;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import reactor.core.publisher.Flux;

/**
 * 
 * Integration Client class definition to execute RESTful requests with any
 * external system needed by busyflights over HTTP. 
 * 
 * So far this class only exposes POST methods, but in the future if need 
 * be new methods might be added for GET,PUT,PATCH,DELETE.
 *
 */
@Component
@PropertySource("classpath:busyflights-service.properties")
public class IntegratorWebClient {

	@Autowired
	private WebClientTemplate clientTemplate;

	@Value("${busyflights.client.message.emptyRequestBody}")
	private String emptyRequestBodyMessage;

	public <T> Flux<T> exchangeGet(@NotNull @NotBlank @Valid final String baseIntegrationUrl,
			final Map<String, List<String>> queryParams, final List<String> pathVariables, List<String> uriPathSegments,
			final Map<String, String> additionalHeaders, final Class<T> responseClass) {
		return clientTemplate.executeGet(getIntegrationUrl(baseIntegrationUrl, pathVariables),
				builder -> builder.pathSegment(getPathReplacers(uriPathSegments))
						.queryParams(getQueryParametersAsMultiValueMap(queryParams)).build(),
				additionalHeaders, responseClass);
	}

	public <R, T> Flux<T> exchangePost(@NotNull @NotBlank @Valid final String baseIntegrationUrl, final R requestBody,
			final List<String> pathVariables, final List<String> uriPathSegments,
			final Map<String, String> additionalHeaders, final Class<T> responseClass) {
		Objects.requireNonNull(requestBody, MessageFormat.format(emptyRequestBodyMessage, HttpMethod.POST.name()));
		return clientTemplate.executePost(getIntegrationUrl(baseIntegrationUrl, pathVariables),
				builder -> builder.pathSegment(getPathReplacers(uriPathSegments)).build(), additionalHeaders,
				requestBody, responseClass);
	}

	private String getIntegrationUrl(final String baseInterationUrl, final List<String> pathVariables) {
		return MessageFormat.format(baseInterationUrl,
				!CollectionUtils.isEmpty(pathVariables) ? pathVariables.toArray(new Object[pathVariables.size()])
						: null);
	}

	private String[] getPathReplacers(final List<String> uriPathSegments) {
		return !CollectionUtils.isEmpty(uriPathSegments) ? uriPathSegments.toArray(new String[uriPathSegments.size()])
				: new String[] {};
	}

	private MultiValueMap<String, String> getQueryParametersAsMultiValueMap(
			final Map<String, List<String>> queryParams) {
		final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.putAll(CollectionUtils.isEmpty(queryParams) ? new HashMap<>() : queryParams);
		return parameters;
	}

}
