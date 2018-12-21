package com.travix.medusa.busyflights.domain.toughjet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.nio.charset.Charset;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.travix.medusa.busyflights.supplier.integrator.http.webclient.IntegratorWebClient;
import com.travix.medusa.busyflights.utility.StubsFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class ToughJetApiIntegratorTest {

	@Mock
	private IntegratorWebClient client;

	@InjectMocks
	private ToughJetApiIntegrator toughJetApiIntegrator;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(toughJetApiIntegrator, "toughJetBaseIntegrationUrl",
				"https://crazyair.nl/flights/search");
	}

	@Test
	void testIntegratePostApi_SuccessResponse() {
		when(client.exchangePost(anyString(), any(ToughJetRequest.class), anyList(), anyList(), anyMap(),
				eq(ToughJetResponse.class)))
						.thenReturn(Flux.fromIterable(StubsFactory.getStubToughJetResponseAsList()));
		StepVerifier
				.create(toughJetApiIntegrator.integratePostApi(StubsFactory.getStubToughJetRequest(),
						Collections.emptyList(), Collections.emptyList()))
				.expectNextSequence(StubsFactory.getStubToughJetResponseAsList()).expectComplete().verify();

	}

	@Test
	void testIntegratePostApi_ClientError() {

		when(client.exchangePost(anyString(), any(ToughJetRequest.class), anyList(), anyList(), anyMap(),
				eq(ToughJetResponse.class)))
						.thenReturn(Flux.error(new WebClientResponseException(HttpStatus.BAD_REQUEST.value(),
								"Bad Request", new HttpHeaders(), null, Charset.defaultCharset())));
		StepVerifier.create(toughJetApiIntegrator.integratePostApi(StubsFactory.getStubToughJetRequest(),
				Collections.emptyList(), Collections.emptyList())).expectError(RuntimeException.class).verify();
	}

	@Test
	void testIntegratePostApi_ServerErrror() {
		when(client.exchangePost(anyString(), any(ToughJetRequest.class), anyList(), anyList(), anyMap(),
				eq(ToughJetResponse.class)))
						.thenReturn(Flux.error(new WebClientResponseException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
								"Internal Server Error", new HttpHeaders(), null, Charset.defaultCharset())));
		StepVerifier.create(toughJetApiIntegrator.integratePostApi(StubsFactory.getStubToughJetRequest(),
				Collections.emptyList(), Collections.emptyList())).expectComplete().verify();
	}
}
