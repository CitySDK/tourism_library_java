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
 * Representation of the GML_CE_GEOMETRY class of the UML diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>A {@link Geometry} is composed of one coordinate or a list of coordinates.</p>
 * 
 * @author Pedro Cruz
 *
 */
public class Geometry {
	private static final String srsName = "http://www.opengis.net/def/crs/EPSG/0/4326";
	private String posList;
	
	/**
	 * Gets the coordinates of the geometry. A base position list (a pair of coordinates)
	 * will be in the format <longitude latitude>.
	 * @return postList the coordinates of the geometry.
	 */
	public String getPosList() {
		return posList;
	}
	
	/**
	 * Sets the given coordinates as the list of coordinates.
	 * @param posList the list of coordinates. It should be in the form of <longitude latitude>.
	 */
	public void setPosList(String posList) {
		this.posList = posList;
	}
	
	/**
	 * Gets the opengis srs name URL
	 * @return the opengis srs name
	 */
	public static String getSrsname() {
		return srsName;
	}
}
