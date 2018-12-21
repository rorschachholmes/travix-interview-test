package com.travix.medusa.busyflights.supplier.integrator.http;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Integrator service resolver class that resolves {@link PostApiIntegrator}
 * 
 */
@Component
@SuppressWarnings("unchecked")
public class IntegratorResolver {

	@Autowired
	private List<SupplierHttpApiIntegrator> integrationServices;

	private static final Map<String, SupplierHttpApiIntegrator> integratorClassMap = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() {
		integrationServices.forEach(service -> integratorClassMap.put(service.getClass().getName(), service));
	}

	/**
	 * 
	 * Resolver method to resolve a {@link PostApiIntegrator}
	 * 
	 * @param postApiIntegratorClass the specific {@link PostApiIntegrator} class
	 *                               passed at runtime
	 * @return {@link PostApiIntegrator}
	 */
	public <R, T> PostApiIntegrator<R, T> resolvePostApiIntegrator(
			final Class<? extends PostApiIntegrator<R, T>> postApiIntegratorClass) {
		return (PostApiIntegrator<R, T>) integratorClassMap.get(postApiIntegratorClass.getName());

	}
}
