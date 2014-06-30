/**
 * COPYRIGHT NOTICE: 
 *
 * This file is part of CitySDK WP5 Tourism Java Library.
 *
 * CitySDK WP5 Tourism Java Library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CitySDK WP5 Tourism Java Library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with CitySDK WP5 Tourism Java Library. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright 2013, IST
 */
package citysdk.tourism.client.poi.single;

import citysdk.tourism.client.poi.lists.ListPointOfInterest;

/**
 * A simple representation of a {@link Route}. 
 * <p>A {@link Route} is a set of {@link PointOfInterest} ordered by a given theme - represented by a {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}.</p>
 * 
 * @author Pedro Cruz
 *
 */
public class Route extends POI {
	private ListPointOfInterest pois;
	
	/**
	 * Initializes an empty {@link Route} with an empty {@link citysdk.tourism.client.poi.lists.ListPointOfInterest}.
	 */
	public Route() {
		super();
		pois = new ListPointOfInterest();
	}
	
	/**
	 * Adds a {@link PointOfInterest} to the {@link Route}.
	 * @param poi the {@link PointOfInterest} to be added.
	 */
	public void addPoi(PointOfInterest poi) {
		pois.add(poi);
	}
	
	/**
	 * Gets the number of {@link PointOfInterest}s that the {@link Route} has.
	 * @return the number of total {@link PointOfInterest}.
	 */
	public int getNumPois() {
		return pois.size();
	}
	
	/**
	 * Gets the {@link PointOfInterest} in a given index of a {@link Route}.
	 * @param i the index of the {@link PointOfInterest}.
	 * @return the {@link PointOfInterest} stored in the index.
	 */
	public PointOfInterest getPoi(int i) {
		return pois.get(i);
	}

	/**
	 * Gets the {@link citysdk.tourism.client.poi.lists.ListPointOfInterest} of the {@link Route}.
	 * @return the {@link citysdk.tourism.client.poi.lists.ListPointOfInterest} of the {@link Route}.
	 */
	public ListPointOfInterest getListPoi() {
		return pois;
	}
}
