package citysdk.tourism.client.poi.lists;

import citysdk.tourism.client.poi.single.POI;

/**
 * A list of lists (containing {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}, 
 * {@link citysdk.tourism.client.poi.lists.ListEvent} and {@link citysdk.tourism.client.poi.lists.ListRoute}).
 * 
 * @author Pedro Cruz
 *
 */
public class ListPOIS extends POI {
	private ListPointOfInterest listPoi;
	private ListEvent listEvent;
	private ListRoute listRoute;
	
	public ListPOIS() {
		listPoi = new ListPointOfInterest();
		listEvent = new ListEvent();
		listRoute = new ListRoute();
	}

	public ListPointOfInterest getListPoi() {
		return listPoi;
	}

	public void setListPoi(ListPointOfInterest listPoi) {
		this.listPoi = listPoi;
	}

	public ListEvent getListEvent() {
		return listEvent;
	}

	public void setListEvent(ListEvent listEvent) {
		this.listEvent = listEvent;
	}

	public ListRoute getListRoute() {
		return listRoute;
	}

	public void setListRoute(ListRoute listRoute) {
		this.listRoute = listRoute;
	}
	
	/**
	 * Checks whether this list has Points of Interest
	 * @return <code>true</code> if it has points of interest, <code>false</code> otherwise
	 */
	public boolean hasPois() {
		return this.listPoi.getNumPois() > 0;
	}
	
	/**
	 * Checks whether this list has Events
	 * @return <code>true</code> if it has events, <code>false</code> otherwise
	 */
	public boolean hasEvents() {
		return this.listEvent.getNumEvents() > 0;
	}
	
	/**
	 * Checks whether this list has Routes
	 * @return <code>true</code> if it has routes, <code>false</code> otherwise
	 */
	public boolean hasRoutes() {
		return this.listRoute.getNumRoutes() > 0;
	}
}
