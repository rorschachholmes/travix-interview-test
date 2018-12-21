package com.travix.medusa.busyflights.domain.mapper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 
 * Resolver class definition to resolve a mapper at runtime
 *
 */
@Component
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MapperResolver {

	@Autowired
	private List<Function> mappers;

	private static final Map<String, Function> mapperClassMap = new ConcurrentHashMap<>();

	@PostConstruct
	void init() {
		mappers.forEach(mapper -> mapperClassMap.put(mapper.getClass().getName(), mapper));
	}

	/**
	 * Resolver method to resolve a mapper
	 * 
	 * @param mapperClass the concrete implementation class needed at runtime
	 * @return {@link Function} representing the mapper
	 */
	public <R, T> Function<R, T> resolveMapper(final Class<? extends Function<R, T>> mapperClass) {
		return mapperClassMap.get(mapperClass.getName());
	}
}
