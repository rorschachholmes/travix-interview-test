package com.travix.medusa.busyflights.supplier.integrator.http;

/**
 * 
 * Marker interface to denote implementation classes or sub-interfaces as type
 * of an integrator over HTTP with a supplier system
 * 
 * {@link PostApiIntegrator}
 * 
 * If in the future a supplier exposes an API with a different http method like
 * GET this interface can be extended by a class like {GetApiIntegrator}.
 *
 */
public interface SupplierHttpApiIntegrator {
}
