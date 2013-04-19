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
 * Representation of the POITermType class of the UML Diagram found in:
 * <a href="http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model">http://www.w3.org/2010/POI/documents/Core/core-20111216.html#poi-data-model.</a>
 * 
 * <p>The {@link POITermType} entity is an abstract entity derived from {@link POIBaseType} and adds properties 
 * for the management of categorical descriptions. The category, link, label, author, license 
 * and time entities in addition to the geometry elements point, line and polygon entities 
 * are all instances of POITermType.</p>
 * 
 * <p>It is composed of:</p>
 * <ul>
 * <li>term - a machine-readable character string to designate any number of discrete choices;</li>
 * <li>scheme - an absolute reference to the schema enumerating the discrete choices in term;</li>
 * </ul>
 * 
 * @author Pedro Cruz
 *
 */
public class POITermType extends POIBaseType {
	private String term;
	private String scheme;
	
	public POITermType() {
		super();
		term = new String("");
		scheme = new String("");
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
}
