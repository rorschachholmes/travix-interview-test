package com.travix.medusa.busyflights.utility;

import java.math.BigDecimal;

/**
 * Util class for fare related calculations
 * 
 */
public final class FareCalculationUtils {

	private static final BigDecimal DIVISOR = new BigDecimal(100);

	/**
	 * private constructor
	 */
	private FareCalculationUtils() {
		// util class shouldn't be instantiated
	}

	/**
	 * 
	 * Calculate Fare by discount percentage
	 * 
	 * @param fare
	 * @param rate
	 * @return discounted fare
	 */
	public static double calculateFareByDiscount(final double fare, final double discount) {
		return discount != 0.0 ? BigDecimal.valueOf(fare)
				.subtract(BigDecimal.valueOf(fare).multiply(BigDecimal.valueOf(discount).divide(DIVISOR))).doubleValue()
				: fare;
	}
}
