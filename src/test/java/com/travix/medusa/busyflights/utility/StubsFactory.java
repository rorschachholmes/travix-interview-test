package com.travix.medusa.busyflights.utility;

import java.util.Arrays;
import java.util.List;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

public final class StubsFactory {

	private StubsFactory() {
	}

	public static BusyFlightsRequest getStubBusyFlightsRequest() {
		return TestUtils.convertJsonToObject("busyFlightsRequestStub.json", BusyFlightsRequest.class);
	}

	public static CrazyAirRequest getStubCrazyAirRequest() {
		return TestUtils.convertJsonToObject("crazyAirRequestStub.json", CrazyAirRequest.class);
	}

	public static ToughJetRequest getStubToughJetRequest() {
		return TestUtils.convertJsonToObject("toughJetRequestStub.json", ToughJetRequest.class);
	}

	public static List<BusyFlightsResponse> getStubBusyFlightsResponseFromCrazyAirAsList() {
		return Arrays.asList(TestUtils.convertJsonToObject("busyFlightsResponseFromCrazyAirResultsStub.json",
				BusyFlightsResponse[].class));
	}

	public static List<BusyFlightsResponse> getStubAggregateBusyFlightsResponseSortByFareAscendingAsList() {
		return Arrays.asList(
				TestUtils.convertJsonToObject("aggrgateSearchResultSortByFareStub.json", BusyFlightsResponse[].class));
	}

	public static List<BusyFlightsResponse> getStubBusyFlightsResponseFromToughJetAsList() {
		return Arrays.asList(TestUtils.convertJsonToObject("busyFlightsResponseFromToughJetResultsStub.json",
				BusyFlightsResponse[].class));
	}

	public static List<CrazyAirResponse> getStubCrazyAirResponseAsList() {
		return Arrays.asList(TestUtils.convertJsonToObject("crazyAirResponseStub.json", CrazyAirResponse[].class));
	}

	public static List<ToughJetResponse> getStubToughJetResponseAsList() {
		return Arrays.asList(TestUtils.convertJsonToObject("toughJetResponseStub.json", ToughJetResponse[].class));
	}

}
