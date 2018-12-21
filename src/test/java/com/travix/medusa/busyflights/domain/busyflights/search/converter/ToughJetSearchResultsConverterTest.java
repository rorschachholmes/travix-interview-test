package com.travix.medusa.busyflights.domain.busyflights.search.converter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.mapper.MapperResolver;
import com.travix.medusa.busyflights.domain.mapper.ToughJetRequestMapper;
import com.travix.medusa.busyflights.domain.mapper.ToughJetResponseMapper;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetApiIntegrator;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.supplier.integrator.http.IntegratorResolver;
import com.travix.medusa.busyflights.utility.StubsFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ToughJetSearchResultsConverterTest {

	@Mock
	private IntegratorResolver integratorResolver;

	@Mock
	private ToughJetApiIntegrator toughJetApiIntegrator;

	@Mock
	private MapperResolver mapperResolver;

	@Mock
	private ToughJetRequestMapper toughJetRequestMapper;

	@Mock
	private ToughJetResponseMapper toughJetResponseMapper;

	@InjectMocks
	private ToughJetSearchResultsConverter toughJetSearchResultsConverter;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(integratorResolver.resolvePostApiIntegrator(ToughJetApiIntegrator.class))
				.thenReturn(toughJetApiIntegrator);
	}

	@Test
	void testConvertSupplierResultsFromRequest() {
		when(toughJetApiIntegrator.integratePostApi(any(ToughJetRequest.class), anyList(), anyList()))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubToughJetResponseAsList()));
		doReturn(toughJetRequestMapper).when(mapperResolver).resolveMapper(ToughJetRequestMapper.class);
		doReturn(toughJetResponseMapper).when(mapperResolver).resolveMapper(ToughJetResponseMapper.class);
		doCallRealMethod().when(toughJetRequestMapper).apply(any(BusyFlightsRequest.class));
		doCallRealMethod().when(toughJetResponseMapper).apply(any(ToughJetResponse.class));
		StepVerifier
				.create(toughJetSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextSequence(StubsFactory.getStubBusyFlightsResponseFromToughJetAsList()).expectComplete()
				.verify();

	}

	@Test
	public void testConvertSupplierResultsFromRequest_NoResults() {
		doReturn(toughJetRequestMapper).when(mapperResolver).resolveMapper(ToughJetRequestMapper.class);
		doReturn(toughJetResponseMapper).when(mapperResolver).resolveMapper(ToughJetResponseMapper.class);
		doCallRealMethod().when(toughJetRequestMapper).apply(any(BusyFlightsRequest.class));
		when(toughJetApiIntegrator.integratePostApi(any(ToughJetRequest.class), anyList(), anyList()))
				.thenReturn(Flux.empty());
		StepVerifier
				.create(toughJetSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectComplete().verify();

	}

	@Test
	public void testConvertSupplierResultsFromRequest_Error() {
		doReturn(toughJetRequestMapper).when(mapperResolver).resolveMapper(ToughJetRequestMapper.class);
		doReturn(toughJetResponseMapper).when(mapperResolver).resolveMapper(ToughJetResponseMapper.class);
		doCallRealMethod().when(toughJetRequestMapper).apply(any(BusyFlightsRequest.class));
		when(toughJetApiIntegrator.integratePostApi(any(ToughJetRequest.class), anyList(), anyList()))
				.thenReturn(Flux.error(new RuntimeException()));

		StepVerifier
				.create(toughJetSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectError(RuntimeException.class).verify();

	}
}
