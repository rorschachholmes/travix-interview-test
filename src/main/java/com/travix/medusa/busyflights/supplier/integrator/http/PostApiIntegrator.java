package com.travix.medusa.busyflights.supplier.integrator.http;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.reactivestreams.Publisher;

/**
 * 
 * API Integrator interface to execute HTTP POST with a supplier system
 * 
 * @param <R>
 * @param <T>
 */
public interface PostApiIntegrator<R, T> extends SupplierHttpApiIntegrator {

	/**
	 * Abstract method to execute an HTTP POST operation
	 * 
	 * @param requestBody     <R> This is essentially the request body.
	 * @param queryParams     this is where the invoker sets query parameters as a
	 *                        {@link Map}.
	 * 
	 * @param pathVariables   {@link List} of path variables which has placeholder
	 *                        in the base integration URL
	 * 
	 * @param uriPathSegments {@link List} of path segments which needs to appended
	 *                        to the base integration URL to identify a particular
	 *                        resource
	 * 
	 * 
	 *                        <pre>
	 * E.g :
	 * 
	 *      Sample Complete URI -
	 *      http://domain.com/resource/{resourceId}/subresource/{subresourceId}
	 * 
	 *      If,
	 *  
	 *      Base URI is http://domain.com/resource/{resourceId}/subresource
	 * 
	 *      Then,
	 * 
	 *      1. pathVariables means [{resourceId}]
	 * 
	 *      2. uriPathSegments means [{subresourceId}]
	 * 
	 *                        </pre>
	 * 
	 * @return a {@link Publisher} emitting data of type <T>
	 * 
	 * @see implementation details in the implementing classes
	 */
	Publisher<T> integratePostApi(@NotNull @Valid final R requestBody, final List<String> pathVariables,
			final List<String> uriPathSegments);

}
