package com.travix.medusa.busyflights.domain.busyflights.search.converter;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.busyflights.Supplier;
import com.travix.medusa.busyflights.domain.mapper.MapperResolver;
import com.travix.medusa.busyflights.domain.mapper.ToughJetRequestMapper;
import com.travix.medusa.busyflights.domain.mapper.ToughJetResponseMapper;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetApiIntegrator;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.supplier.integrator.http.IntegratorResolver;

import reactor.core.publisher.Flux;

/**
 * A concrete implementation of {@link SupplierSearchResultsConverter}
 * 
 * This class evaluates search results based on {@link BusyFlightsRequest} from
 * the ToughJet supplier system and converting those results in the form ofs
 * {@link BusyFlightsResponse}
 * 
 */
@Component
public class ToughJetSearchResultsConverter implements SupplierSearchResultsConverter {

	@Autowired
	private MapperResolver mapperResolver;

	@Autowired
	private IntegratorResolver integratorResolver;

	/**
	 * Conversion logic to convert search results from ToughJet based on
	 * {@link BusyFlightsRequest} by mapping it to the {@link ToughJetRequest}, then
	 * calling ToughJet over HTTP with that request and finally mapping back the
	 * {@link ToughJetResponse} to the {@link BusyFlightsResponse}
	 */
	@Override
	public Flux<BusyFlightsResponse> convertSupplierResultsFromRequest(final BusyFlightsRequest busyFlightsRequest) {
		return Flux.just(busyFlightsRequest).map(mapperResolver.resolveMapper(ToughJetRequestMapper.class))
				.flatMap(crazyAirRequest -> integratorResolver.resolvePostApiIntegrator(ToughJetApiIntegrator.class)
						.integratePostApi(crazyAirRequest, Collections.emptyList(), Collections.emptyList()))
				.map(mapperResolver.resolveMapper(ToughJetResponseMapper.class));
	}

	@Override
	public Supplier getSupplierType() {
		return Supplier.TOUGHJET;
	}

}
