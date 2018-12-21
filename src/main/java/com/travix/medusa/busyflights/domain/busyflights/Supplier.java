package com.travix.medusa.busyflights.domain.busyflights;

/**
 * 
 * Enum to specify different supplier types. For any future supplier, its name
 * should be registered in this Enum
 * 
 */
public enum Supplier {

	CRAZYAIR("CrazyAir"), TOUGHJET("ToughJet");

	private final String supplierName;

	private Supplier(final String supplierName) {
		this.supplierName = supplierName;
	}

	public String getSupplierName() {
		return supplierName;
	}

}
