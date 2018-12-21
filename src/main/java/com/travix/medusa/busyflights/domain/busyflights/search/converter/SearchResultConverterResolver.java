package com.travix.medusa.busyflights.domain.busyflights.search.converter;

import java.util.EnumMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.travix.medusa.busyflights.domain.busyflights.Supplier;

/**
 * 
 * Resolver class definition to resolve a {@link SupplierSearchResultsConverter} at
 * runtime
 *
 */
@Component
public class SearchResultConverterResolver {

	@Autowired
	private List<SupplierSearchResultsConverter> converters;

	private static final EnumMap<Supplier, SupplierSearchResultsConverter> converterMap = new EnumMap<>(Supplier.class);

	@PostConstruct
	void init() {
		converters.forEach(converter -> converterMap.put(converter.getSupplierType(), converter));
	}

	/**
	 * Resolver method to resolve a {@link SupplierSearchResultsConverter}
	 * 
	 * @param supplier the supplier type for which the converter is needed
	 * @return {@link SupplierSearchResultsConverter}
	 */
	public SupplierSearchResultsConverter resolveConverter(final Supplier supplier) {
		return converterMap.get(supplier);
	}

}
