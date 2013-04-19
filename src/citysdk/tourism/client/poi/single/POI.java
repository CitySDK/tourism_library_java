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

import citysdk.tourism.client.poi.Deserializable;
import citysdk.tourism.client.poi.base.Location;
import citysdk.tourism.client.poi.base.POIType;

/**
 * A base class for {@link PointOfInterest}, {@link Event}, {@link Route} and {@link citysdk.tourism.client.poi.lists.POIS}.
 * <p>A {@link POI} is a {@link POIType} which stores a {@link Location}.</p>
 *  
 * @author Pedro Cruz
 *
 */
public class POI extends POIType implements Deserializable {
	private Location location;
	
	/**
	 * Initializes an empty W3C.
	 */
	public POI() {
		super();
		location = new Location();
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * Checks whether the {@link POI} has an address associated
	 * @return <code>true</code> if it has an address, <code>false</code> otherwise
	 */
	public boolean hasAddress() {
		return (location.getAddress().getType() != null 
				&& location.getAddress().getValue() != null);
	}
}
