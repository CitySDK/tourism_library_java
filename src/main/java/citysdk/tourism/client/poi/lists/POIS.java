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

import java.util.ArrayList;
import java.util.List;

import citysdk.tourism.client.poi.single.POI;

/**
 * The representation of a list of {@link citysdk.tourism.client.poi.single.POI}.
 * 
 * @author Pedro Cruz
 *
 * @param <T> an object extending a poi base class, either a {@link citysdk.tourism.client.poi.single.PointOfInterest},
 * {@link citysdk.tourism.client.poi.single.Event}, {@link citysdk.tourism.client.poi.single.Route} or {@link citysdk.tourism.client.poi.single.Tag}.
 */
public class POIS<T> extends POI {
	private List<T> pois;
	
	/**
	 * Creates an empty list.
	 */
	public POIS() {
		pois = new ArrayList<T>();
	}
	
	/**
	 * Adds an object to the list
	 * @param t the object to be added
	 */
	public void add(T t) {
		pois.add(t);
	}

	/**
	 * Gets all the objects stored
	 * @return the list of objects
	 */
	public List<T> getList() {
		return pois;
	}
	
	/**
	 * Gets the number of objects stored
	 * @return the total number of objects
	 */
	public int size() {
		return pois.size();
	}

	/**
	 * Gets the object stored in the given index
	 * @param i the index of a given object
	 * @return the object stored in the given index or null
	 */
	public T get(int i) {
		if(i < pois.size())
			return pois.get(i);
		else
			return null;
	}
}
