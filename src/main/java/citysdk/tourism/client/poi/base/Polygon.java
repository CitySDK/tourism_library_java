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
package citysdk.tourism.client.poi.base;

/**
 * Representation of the Polygon class of the UML Diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>A {@link Polygon} is a representation of a Represented by a {@link Geometry}, basically having three or more coordinates.</p>
 * 
 * @author Pedro Cruz
 * 
 */
public class Polygon extends POITermType {
	private Geometry simplePolygon;
	
	public Polygon() {
		super();
		simplePolygon = null;
	}

	/**
	 * Returns the polygon representing this {@link Polygon}.
	 * @return {@link Geometry} of this {@link Polygon}.
	 */
	public Geometry getSimplePolygon() {
		return simplePolygon;
	}

	/**
	 * Sets the geometry that represents this {@link Polygon}
	 * @param simplePolygon the new {@link Polygon}.
	 */
	public void setSimplePolygon(Geometry simplePolygon) {
		this.simplePolygon = simplePolygon;
	}
}
