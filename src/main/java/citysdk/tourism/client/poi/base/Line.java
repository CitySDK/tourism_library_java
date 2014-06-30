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
 * Representation of the Line class of the UML Diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>A {@link Line} is a representation of a {@link Geometry}, basically having two points and thus two pairs of coordinates.</p>
 * 
 * @author Pedro Cruz
 * 
 */
public class Line extends POITermType {
	private Geometry lineString;

	/**
	 * Initializes an empty {@link Line}.
	 */
	public Line() {
		super();
		lineString = null;
	}

	/**
	 * Returns the {@link Geometry} representing this {@link Line}.
	 * @return {@link Geometry} of this {@link Line}.
	 */
	public Geometry getLineString() {
		return lineString;
	}

	/**
	 * Sets the {@link Geometry} that represents this {@link Line}.
	 * @param lineString the new line.
	 */
	public void setLineString(Geometry lineString) {
		this.lineString = lineString;
	}

}
