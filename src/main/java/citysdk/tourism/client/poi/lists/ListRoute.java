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
package citysdk.tourism.client.poi.lists;

import java.util.List;

import citysdk.tourism.client.poi.single.Route;

/**
 * Represents a list of {@link citysdk.tourism.client.poi.single.Route}.
 * 
 * @author Pedro Cruz
 *
 */
public class ListRoute extends POIS<Route> {
	
	/**
	 * Creates an empty list of {@link citysdk.tourism.client.poi.single.Route}.
	 */
	public ListRoute(){
		super();
	}
	
	/**
	 * Adds a {@link citysdk.tourism.client.poi.single.Route}.
	 * @param route the {@link citysdk.tourism.client.poi.single.Route} to be added.
	 */
	public void addRoute(Route route) {
		super.add(route);
	}

	/**
	 * Gets all the routes.
	 * @return the list of {@link citysdk.tourism.client.poi.single.Route}.
	 */
	public List<Route> getRoutes() {
		return super.getList();
	}
	
	/**
	 * Gets a given {@link citysdk.tourism.client.poi.single.Route}.
	 * @param i the index of the {@link citysdk.tourism.client.poi.single.Route}.
	 * @return the {@link citysdk.tourism.client.poi.single.Route} stored within the given index.
	 */
	@Override
	public Route get(int i) {
		return super.get(i);
	}

	/**
	 * Returns the number of routes stored.
	 * @return the total number of routes.
	 */
	public int getNumRoutes() {
		return super.size();
	}
}
