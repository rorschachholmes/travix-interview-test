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
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirApiIntegrator;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.mapper.CrazyAirRequestMapper;
import com.travix.medusa.busyflights.domain.mapper.CrazyAirResponseMapper;
import com.travix.medusa.busyflights.domain.mapper.MapperResolver;
import com.travix.medusa.busyflights.supplier.integrator.http.IntegratorResolver;
import com.travix.medusa.busyflights.utility.StubsFactory;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CrazyAirSearchResultsConverterTest {

	@Mock
	private IntegratorResolver integratorResolver;

	@Mock
	private CrazyAirApiIntegrator crazyAirApiIntegrator;

	@Mock
	private MapperResolver mapperResolver;

	@Mock
	private CrazyAirRequestMapper crazyAirRequestMapper;

	@Mock
	private CrazyAirResponseMapper crazyAirResponseMapper;

	@InjectMocks
	private CrazyAirSearchResultsConverter crazyAirSearchResultsConverter;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		when(integratorResolver.resolvePostApiIntegrator(CrazyAirApiIntegrator.class))
				.thenReturn(crazyAirApiIntegrator);
		
	}

	@Test
	public void testConvertSupplierResultsFromRequest() {

		when(crazyAirApiIntegrator.integratePostApi(any(CrazyAirRequest.class), anyList(), anyList()))
				.thenReturn(Flux.fromIterable(StubsFactory.getStubCrazyAirResponseAsList()));
		doReturn(crazyAirRequestMapper).when(mapperResolver).resolveMapper(CrazyAirRequestMapper.class);
		doReturn(crazyAirResponseMapper).when(mapperResolver).resolveMapper(CrazyAirResponseMapper.class);
		doCallRealMethod().when(crazyAirRequestMapper).apply(any(BusyFlightsRequest.class));
		doCallRealMethod().when(crazyAirResponseMapper).apply(any(CrazyAirResponse.class));
		StepVerifier
				.create(crazyAirSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectNextSequence(StubsFactory.getStubBusyFlightsResponseFromCrazyAirAsList()).expectComplete()
				.verify();

	}

	@Test
	public void testConvertSupplierResultsFromRequest_NoResults() {
		doReturn(crazyAirRequestMapper).when(mapperResolver).resolveMapper(CrazyAirRequestMapper.class);
		doReturn(crazyAirResponseMapper).when(mapperResolver).resolveMapper(CrazyAirResponseMapper.class);
		doCallRealMethod().when(crazyAirRequestMapper).apply(any(BusyFlightsRequest.class));
		when(crazyAirApiIntegrator.integratePostApi(any(CrazyAirRequest.class), anyList(), anyList()))
				.thenReturn(Flux.empty());
		StepVerifier
				.create(crazyAirSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectComplete().verify();

	}

	@Test
	public void testConvertSupplierResultsFromRequest_Error() {
		doReturn(crazyAirRequestMapper).when(mapperResolver).resolveMapper(CrazyAirRequestMapper.class);
		doReturn(crazyAirResponseMapper).when(mapperResolver).resolveMapper(CrazyAirResponseMapper.class);
		doCallRealMethod().when(crazyAirRequestMapper).apply(any(BusyFlightsRequest.class));
		when(crazyAirApiIntegrator.integratePostApi(any(CrazyAirRequest.class), anyList(), anyList()))
				.thenReturn(Flux.error(new RuntimeException()));

		StepVerifier
				.create(crazyAirSearchResultsConverter
						.convertSupplierResultsFromRequest(StubsFactory.getStubBusyFlightsRequest()))
				.expectError(RuntimeException.class).verify();

	}

}
