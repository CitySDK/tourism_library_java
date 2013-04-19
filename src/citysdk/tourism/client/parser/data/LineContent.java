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
package citysdk.tourism.client.parser.data;

/**
 * Container of a line. A line is no more than two {@link LocationContent}.
 * 
 * @author Pedro Cruz
 *
 */
public class LineContent implements GeometryContent {
	private LocationContent pointOne;
	private LocationContent pointTwo;

	/**
	 * Constructor
	 * @param pointOne point one of the line
	 * @param pointTwo point two of the line
	 */
	public LineContent(LocationContent pointOne, LocationContent pointTwo) {
		this.pointOne = pointOne;
		this.pointTwo = pointTwo;
	}
	
	public LocationContent getPointOne() {
		return pointOne;
	}

	public LocationContent getPointTwo() {
		return pointTwo;
	}

	@Override
	public int getNumGeo() {
		return 2;
	}
}
