package com.travix.medusa.busyflights.domain.busyflights.search.converter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirApiIntegrator;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.mapper.CrazyAirRequestMapper;
import com.travix.medusa.busyflights.domain.mapper.CrazyAirResponseMapper;
import com.travix.medusa.busyflights.domain.mapper.MapperResolver;
import com.travix.medusa.busyflights.supplier.integrator.http.IntegratorResolver;

import reactor.core.publisher.Flux;

/**
 * A concrete implementation of {@link SupplierSearchResultsConverter}
 * 
 * This class evaluates search results based on {@link BusyFlightsRequest} from
 * the CrazyAir supplier system and converting those results in the form of
 * {@link BusyFlightsResponse}
 * 
 */
@Component
public class CrazyAirSearchResultsConverter implements SupplierSearchResultsConverter {

	@Autowired
	private MapperResolver mapperResolver;

	@Autowired
	private IntegratorResolver integratorResolver;

	/**
	 * Conversion logic to convert search results from CrazyAir based on
	 * {@link BusyFlightsRequest} by mapping it to the {@link CrazyAirRequest}, then
	 * calling CrazyAir over HTTP with that request and finally mapping back the
	 * {@link CrazyAirResponse} to the {@link BusyFlightsResponse}
	 */
	@Override
	public Flux<BusyFlightsResponse> convertSupplierResultsFromRequest(final BusyFlightsRequest busyFlightsRequest) {
		return Flux.just(busyFlightsRequest).map(mapperResolver.resolveMapper(CrazyAirRequestMapper.class))
				.flatMap(crazyAirRequest -> integratorResolver.resolvePostApiIntegrator(CrazyAirApiIntegrator.class)
						.integratePostApi(crazyAirRequest, Collections.emptyList(), Collections.emptyList()))
				.map(mapperResolver.resolveMapper(CrazyAirResponseMapper.class));
	}

	@Override
	public Supplier getSupplierType() {
		return Supplier.CRAZYAIR;
	}

}
